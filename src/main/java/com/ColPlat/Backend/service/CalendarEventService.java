package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.CalendarEventRequest;
import com.ColPlat.Backend.model.dto.request.CalendarViewRequest;
import com.ColPlat.Backend.model.dto.response.CalendarEventResponse;
import com.ColPlat.Backend.model.enums.ParticipationStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarEventService {
    List<CalendarEventResponse> getUserEvents(String token, CalendarViewRequest request);
    List<CalendarEventResponse> getTeamEvents(String token, Long teamId, LocalDateTime startDate, LocalDateTime endDate);
    CalendarEventResponse createEvent(String token, CalendarEventRequest request);
    CalendarEventResponse updateEvent(String token, Long eventId, CalendarEventRequest request);
    void deleteEvent(String token, Long eventId);
    void respondToEvent(String token, Long eventId, ParticipationStatus response);
}