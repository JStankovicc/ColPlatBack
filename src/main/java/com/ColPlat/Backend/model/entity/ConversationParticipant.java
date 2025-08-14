package com.ColPlat.Backend.model.entity;

// package com.ColPlat.Backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "_conversation_participant",
        uniqueConstraints = @UniqueConstraint(name="uk_conv_participant", columnNames = {"conversation_id","user_id"}),
        indexes = {
                @Index(name="idx_part_user", columnList = "user_id"),
                @Index(name="idx_part_conv", columnList = "conversation_id")
        })
public class ConversationParticipant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="conversation_id", nullable=false)
    private Conversation conversation;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name="joined_at", nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @Column(name="last_read_at")
    private LocalDateTime lastReadAt;

    @Column(name="muted", nullable = false)
    private boolean muted = false;

    @Column(name="archived", nullable = false)
    private boolean archived = false;
}
