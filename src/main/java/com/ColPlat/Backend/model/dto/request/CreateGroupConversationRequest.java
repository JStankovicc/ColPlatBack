package com.ColPlat.Backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGroupConversationRequest {
    private String name;
    private Set<Long> participantIds; // uključuje i kreatora
}