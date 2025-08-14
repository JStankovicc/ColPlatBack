package com.ColPlat.Backend.repository;

// package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Message;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByConversation_IdOrderByCreatedAtAsc(Long conversationId, Pageable pageable);
    long countByConversation_IdAndCreatedAtAfter(Long conversationId, java.time.LocalDateTime after);
}
