package com.ColPlat.Backend.model.entity;

// package com.ColPlat.Backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity @Table(name = "_conversation",
        indexes = {
                @Index(name="idx_conv_company", columnList = "company_id"),
                @Index(name="idx_conv_lastmsgat", columnList = "last_message_at")
        })
public class Conversation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="company_id", nullable = false)
    private Long companyId;

    @Column(name="is_group", nullable = false)
    private boolean group;

    // za 1-1 razgovore može ostati null; za grupne – naziv grupe
    private String name;

    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name="last_message_at")
    private LocalDateTime lastMessageAt;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ConversationParticipant> participants = new HashSet<>();
}
