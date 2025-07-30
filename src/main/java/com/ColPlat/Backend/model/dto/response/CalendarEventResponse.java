package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.enums.EventPriority;
import com.ColPlat.Backend.model.enums.ParticipationStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CalendarEventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private EventPriority priority;
    private Long createdByUserId;
    private String createdByUserName;
    private Long teamId;
    private String teamName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ParticipantResponse> participants;
    
    @Data
    public static class ParticipantResponse {
        private Long userId;
        private String userName;
        private ParticipationStatus status;
    }
}