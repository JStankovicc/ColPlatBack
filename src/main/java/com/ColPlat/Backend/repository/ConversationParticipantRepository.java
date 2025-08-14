package com.ColPlat.Backend.repository;

// package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.ConversationParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationParticipantRepository extends JpaRepository<ConversationParticipant, Long> {
    Optional<ConversationParticipant> findByConversation_IdAndUserId(Long conversationId, Long userId);
    List<ConversationParticipant> findAllByConversation_Id(Long conversationId);
}
