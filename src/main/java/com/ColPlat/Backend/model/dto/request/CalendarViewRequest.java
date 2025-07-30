package com.ColPlat.Backend.model.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CalendarViewRequest {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long teamId;
}