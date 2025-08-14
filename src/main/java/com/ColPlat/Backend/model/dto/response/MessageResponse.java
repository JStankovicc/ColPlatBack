package com.ColPlat.Backend.model.dto.response;

// package com.ColPlat.Backend.model.dto.chat;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class MessageResponse {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
    private String status; // SENT/DELIVERED/READ
}
