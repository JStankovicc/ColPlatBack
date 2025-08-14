package com.ColPlat.Backend.service.implementation;

// package com.ColPlat.Backend.service.implementation;


import com.ColPlat.Backend.model.dto.response.ConversationSummaryResponse;
import com.ColPlat.Backend.model.dto.response.MessageResponse;
import com.ColPlat.Backend.model.entity.Conversation;
import com.ColPlat.Backend.model.entity.ConversationParticipant;
import com.ColPlat.Backend.model.entity.Message;
import com.ColPlat.Backend.model.enums.MessageStatus;
import com.ColPlat.Backend.repository.ConversationParticipantRepository;
import com.ColPlat.Backend.repository.ConversationRepository;
import com.ColPlat.Backend.repository.MessageRepository;
import com.ColPlat.Backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImplementation implements ChatService {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;
    private final MessageRepository messageRepository;
    private final SimpMessagingTemplate messagingTemplate; // za WebSocket push

    @Override
    @Transactional(readOnly = true)
    public List<ConversationSummaryResponse> getInbox(Long companyId, Long userId) {
        List<Conversation> conversations = conversationRepository.findInbox(companyId, userId);

        return conversations.stream().map(c -> {
            // last message
            String lastPreview = null;
            LocalDateTime lastAt = c.getLastMessageAt();

            // (opciono) možeš optimizovati dodatnim upitom koji vraća poslednju poruku po konverzaciji

            Set<Long> participantIds = c.getParticipants().stream()
                    .map(ConversationParticipant::getUserId)
                    .collect(Collectors.toSet());

            // unread count: jednostavna procena prema lastReadAt
            int unread = c.getParticipants().stream()
                    .filter(p -> Objects.equals(p.getUserId(), userId))
                    .findFirst()
                    .map(p -> {
                        LocalDateTime lra = p.getLastReadAt();
                        if (lra == null || (lastAt != null && lastAt.isAfter(lra))) {
                            // znamo da ima novih; precizan broj može zahtevati dodatni upit
                            return 1;
                        }
                        return 0;
                    }).orElse(0);

            return ConversationSummaryResponse.builder()
                    .conversationId(c.getId())
                    .group(c.isGroup())
                    .name(c.isGroup() ? c.getName() : null) // frontend može prikazati ime druge osobe preko UserResponse
                    .participantIds(participantIds)
                    .lastMessagePreview(lastPreview)
                    .lastMessageAt(lastAt)
                    .unreadCount(unread)
                    .build();
        }).toList();
    }

    @Override
    @Transactional
    public Long createOrGetDirect(Long companyId, Long userId, Long otherUserId) {
        if (Objects.equals(userId, otherUserId)) {
            throw new IllegalArgumentException("Ne možeš pokrenuti direktan razgovor sam sa sobom.");
        }
        return conversationRepository.findDirectBetween(companyId, userId, otherUserId)
                .map(Conversation::getId)
                .orElseGet(() -> {
                    Conversation c = Conversation.builder()
                            .companyId(companyId)
                            .group(false)
                            .name(null)
                            .build();
                    c = conversationRepository.save(c);

                    ConversationParticipant p1 = ConversationParticipant.builder()
                            .conversation(c)
                            .userId(userId)
                            .build();
                    ConversationParticipant p2 = ConversationParticipant.builder()
                            .conversation(c)
                            .userId(otherUserId)
                            .build();
                    participantRepository.saveAll(List.of(p1, p2));
                    return c.getId();
                });
    }

    @Override
    @Transactional
    public Long createGroup(Long companyId, Long creatorUserId, String name, Set<Long> participantIds) {
        if (participantIds == null || participantIds.isEmpty()) {
            throw new IllegalArgumentException("Grupa mora imati učesnike.");
        }
        Conversation c = Conversation.builder()
                .companyId(companyId)
                .group(true)
                .name(name)
                .build();
        c = conversationRepository.save(c);

        Set<Long> all = new HashSet<>(participantIds);
        all.add(creatorUserId);

        Conversation finalC = c;
        List<ConversationParticipant> participants = all.stream()
                .distinct()
                .map(uid -> ConversationParticipant.builder()
                        .conversation(finalC)
                        .userId(uid)
                        .build())
                .toList();
        participantRepository.saveAll(participants);
        return c.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MessageResponse> getMessages(Long conversationId, Long requesterUserId, Pageable pageable) {
        ensureMembership(conversationId, requesterUserId);
        Page<Message> page = messageRepository.findByConversation_IdOrderByCreatedAtAsc(conversationId, pageable);
        return page.map(this::toMessageResponse);
    }

    @Override
    @Transactional
    public MessageResponse sendMessage(Long conversationId, Long senderUserId, String content) {
        Conversation c = ensureMembership(conversationId, senderUserId);

        Message msg = Message.builder()
                .conversation(c)
                .senderId(senderUserId)
                .content(content)
                .status(MessageStatus.SENT)
                .build();
        msg = messageRepository.save(msg);

        c.setLastMessageAt(msg.getCreatedAt());
        conversationRepository.save(c);

        MessageResponse payload = toMessageResponse(msg);

        // push poruku svim klijentima koji su pretplaćeni na thread
        messagingTemplate.convertAndSend("/topic/thread." + conversationId, payload);

        // opciono: notifikacija svakom učesniku u inbox feed
        participantRepository.findAllByConversation_Id(conversationId).forEach(p ->
                messagingTemplate.convertAndSendToUser(
                        String.valueOf(p.getUserId()), // user destination
                        "/queue/inbox",
                        payload
                )
        );

        return payload;
    }

    @Override
    @Transactional
    public void markReadUpTo(Long conversationId, Long userId, Long upToMessageId) {
        ConversationParticipant p = participantRepository
                .findByConversation_IdAndUserId(conversationId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Nisi učesnik konverzacije."));
        p.setLastReadAt(LocalDateTime.now());
        participantRepository.save(p);
        // (opciono) update statusa poruka -> READ
    }

    private Conversation ensureMembership(Long conversationId, Long userId) {
        ConversationParticipant p = participantRepository
                .findByConversation_IdAndUserId(conversationId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Nisi učesnik konverzacije."));
        return p.getConversation();
    }

    private MessageResponse toMessageResponse(Message m) {
        return MessageResponse.builder()
                .id(m.getId())
                .conversationId(m.getConversation().getId())
                .senderId(m.getSenderId())
                .content(m.getContent())
                .createdAt(m.getCreatedAt())
                .status(m.getStatus().name())
                .build();
    }
}
