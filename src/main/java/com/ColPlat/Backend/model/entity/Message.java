package com.ColPlat.Backend.model.entity;



import com.ColPlat.Backend.model.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_message",
        indexes = {
                @Index(name="idx_msg_conv", columnList = "conversation_id"),
                @Index(name="idx_msg_created", columnList = "created_at")
        })
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="conversation_id", nullable=false)
    private Conversation conversation;

    @Column(name="sender_id", nullable=false)
    private Long senderId;

    @Lob
    @Column(name="content", nullable=false)
    private String content;

    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private MessageStatus status = MessageStatus.SENT;
}
