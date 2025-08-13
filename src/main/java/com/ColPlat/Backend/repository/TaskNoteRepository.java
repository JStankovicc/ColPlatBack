package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.TaskNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskNoteRepository extends JpaRepository<TaskNote, Long> {
}
