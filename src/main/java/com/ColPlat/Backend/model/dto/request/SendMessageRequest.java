package com.ColPlat.Backend.model.dto.request;

// package com.ColPlat.Backend.model.dto.chat;

import lombok.*;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SendMessageRequest {
    private Long conversationId;
    private String content;
}
