package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.TaskNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskNoteRepository extends JpaRepository<TaskNote, Long> {
    @Modifying
    @Query(value = "DELETE FROM _task_note WHERE project_task_id = :taskId", nativeQuery = true)
    void deleteByProjectTaskId(@Param("taskId") Long taskId);
}
