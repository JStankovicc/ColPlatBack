package com.ColPlat.Backend.model.dto.response;

// package com.ColPlat.Backend.model.dto.chat;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ConversationSummaryResponse {
    private Long conversationId;
    private boolean group;
    private String name;                  // za grupu; za 1-1 može biti displayName druge osobe
    private Set<Long> participantIds;     // frontend može lookup-ovati UserResponse
    private String lastMessagePreview;
    private LocalDateTime lastMessageAt;
    private Integer unreadCount;
}
