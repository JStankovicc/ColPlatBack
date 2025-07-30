package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.enums.EventPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CalendarEventRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "Start date time is required")
    private LocalDateTime startDateTime;
    
    @NotNull(message = "End date time is required")
    private LocalDateTime endDateTime;
    
    private EventPriority priority;
    
    private Long teamId;
    
    private List<Long> participantUserIds;
}