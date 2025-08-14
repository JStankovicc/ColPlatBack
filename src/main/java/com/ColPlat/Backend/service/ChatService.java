package com.ColPlat.Backend.service;

// package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.ConversationSummaryResponse;
import com.ColPlat.Backend.model.dto.response.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatService {
    List<ConversationSummaryResponse> getInbox(Long companyId, Long userId);
    Long createOrGetDirect(Long companyId, Long userId, Long otherUserId);
    Long createGroup(Long companyId, Long creatorUserId, String name, java.util.Set<Long> participantIds);
    Page<MessageResponse> getMessages(Long conversationId, Long requesterUserId, Pageable pageable);
    MessageResponse sendMessage(Long conversationId, Long senderUserId, String content);
    void markReadUpTo(Long conversationId, Long userId, Long upToMessageId);
}
