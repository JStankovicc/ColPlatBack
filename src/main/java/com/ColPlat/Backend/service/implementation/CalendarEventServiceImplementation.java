package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.CalendarEventRequest;
import com.ColPlat.Backend.model.dto.request.CalendarViewRequest;
import com.ColPlat.Backend.model.dto.response.CalendarEventResponse;
import com.ColPlat.Backend.model.entity.CalendarEvent;
import com.ColPlat.Backend.model.entity.EventParticipant;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.ParticipationStatus;
import com.ColPlat.Backend.repository.CalendarEventRepository;
import com.ColPlat.Backend.repository.EventParticipantRepository;
import com.ColPlat.Backend.service.CalendarEventService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarEventServiceImplementation implements CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;
    private final EventParticipantRepository eventParticipantRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public List<CalendarEventResponse> getUserEvents(String token, CalendarViewRequest request) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        
        List<CalendarEvent> events = calendarEventRepository.findUserEvents(
            user.getId());
        
        return events.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    public List<CalendarEventResponse> getTeamEvents(String token, Long teamId, 
                                                   LocalDateTime startDate, LocalDateTime endDate) {
        List<CalendarEvent> events = calendarEventRepository.findTeamEvents(teamId);
        
        return events.stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CalendarEventResponse createEvent(String token, CalendarEventRequest request) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        
        CalendarEvent event = CalendarEvent.builder()
            .title(request.getTitle())
            .description(request.getDescription())
            .startDateTime(request.getStartDateTime())
            .endDateTime(request.getEndDateTime())
            .priority(request.getPriority())
            .createdByUserId(user.getId())
            .teamId(request.getTeamId())
            .createdAt(LocalDateTime.now())
            .build();
        
        CalendarEvent savedEvent = calendarEventRepository.save(event);
        
        // Add participants
        if (request.getParticipantUserIds() != null) {
            request.getParticipantUserIds().forEach(participantId -> {
                EventParticipant participant = EventParticipant.builder()
                    .event(savedEvent)
                    .userId(participantId)
                    .status(ParticipationStatus.NO_RESPONSE)
                    .build();
                eventParticipantRepository.save(participant);
            });
        }
        
        return convertToResponse(savedEvent);
    }

    @Override
    @Transactional
    public CalendarEventResponse updateEvent(String token, Long eventId, CalendarEventRequest request) {
        CalendarEvent event = calendarEventRepository.findById(eventId)
            .orElseThrow(() -> new RuntimeException("Event not found"));
        
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDateTime(request.getStartDateTime());
        event.setEndDateTime(request.getEndDateTime());
        event.setPriority(request.getPriority());
        event.setUpdatedAt(LocalDateTime.now());
        
        CalendarEvent updatedEvent = calendarEventRepository.save(event);
        return convertToResponse(updatedEvent);
    }

    @Override
    @Transactional
    public void deleteEvent(String token, Long eventId) {
        calendarEventRepository.deleteById(eventId);
    }

    @Override
    @Transactional
    public void respondToEvent(String token, Long eventId, ParticipationStatus response) {
        String username = jwtService.extractUserName(token);
        User user = userService.findByEmail(username);
        
        EventParticipant participant = eventParticipantRepository
            .findByEventIdAndUserId(eventId, user.getId())
            .orElseThrow(() -> new RuntimeException("Participation record not found"));
        
        participant.setStatus(response);
        eventParticipantRepository.save(participant);
    }

    private CalendarEventResponse convertToResponse(CalendarEvent event) {
        CalendarEventResponse response = new CalendarEventResponse();
        response.setId(event.getId());
        response.setTitle(event.getTitle());
        response.setDescription(event.getDescription());
        response.setStartDateTime(event.getStartDateTime());
        response.setEndDateTime(event.getEndDateTime());
        response.setPriority(event.getPriority());
        response.setCreatedByUserId(event.getCreatedByUserId());
        response.setTeamId(event.getTeamId());
        response.setCreatedAt(event.getCreatedAt());
        response.setUpdatedAt(event.getUpdatedAt());
        
        // Load participants
        List<EventParticipant> participants = eventParticipantRepository.findByEventId(event.getId());
        List<CalendarEventResponse.ParticipantResponse> participantResponses = participants.stream()
            .map(p -> {
                CalendarEventResponse.ParticipantResponse pr = new CalendarEventResponse.ParticipantResponse();
                pr.setUserId(p.getUserId());
                pr.setStatus(p.getStatus());
                return pr;
            })
            .collect(Collectors.toList());
        
        response.setParticipants(participantResponses);
        return response;
    }
}