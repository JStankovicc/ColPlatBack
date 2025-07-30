package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.CalendarEventRequest;
import com.ColPlat.Backend.model.dto.request.CalendarViewRequest;
import com.ColPlat.Backend.model.dto.response.CalendarEventResponse;
import com.ColPlat.Backend.model.enums.ParticipationStatus;
import com.ColPlat.Backend.service.CalendarEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/calendar")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarEventService calendarEventService;

    @PostMapping("/events/my")
    public ResponseEntity<List<CalendarEventResponse>> getMyEvents(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody @Valid CalendarViewRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        List<CalendarEventResponse> events = calendarEventService.getUserEvents(token, request);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/events/team/{teamId}")
    public ResponseEntity<List<CalendarEventResponse>> getTeamEvents(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long teamId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        String token = authorizationHeader.replace("Bearer ", "");
        List<CalendarEventResponse> events = calendarEventService.getTeamEvents(token, teamId, startDate, endDate);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/events")
    public ResponseEntity<CalendarEventResponse> createEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody CalendarEventRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        CalendarEventResponse event = calendarEventService.createEvent(token, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @PutMapping("/events/{eventId}")
    public ResponseEntity<CalendarEventResponse> updateEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long eventId,
            @Valid @RequestBody CalendarEventRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        CalendarEventResponse event = calendarEventService.updateEvent(token, eventId, request);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long eventId) {
        String token = authorizationHeader.replace("Bearer ", "");
        calendarEventService.deleteEvent(token, eventId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/events/{eventId}/respond")
    public ResponseEntity<Void> respondToEvent(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long eventId,
            @RequestParam ParticipationStatus response) {
        String token = authorizationHeader.replace("Bearer ", "");
        calendarEventService.respondToEvent(token, eventId, response);
        return ResponseEntity.ok().build();
    }
}