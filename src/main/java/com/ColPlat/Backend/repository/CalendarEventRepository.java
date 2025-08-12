package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {

    @Query("SELECT DISTINCT ce FROM CalendarEvent ce " +
            "LEFT JOIN ce.participants ep " +
            "WHERE (ce.createdByUserId = :userId OR ep.userId = :userId) ")
    List<CalendarEvent> findUserEvents(@Param("userId") Long userId);

    @Query("SELECT ce FROM CalendarEvent ce WHERE ce.createdByUserId = :userId ")
    List<CalendarEvent> findCreatedByUserEvents(@Param("userId") Long userId);

    @Query("SELECT ce FROM CalendarEvent ce WHERE ce.teamId = :teamId ")
    List<CalendarEvent> findTeamEvents(@Param("teamId") Long teamId);
}