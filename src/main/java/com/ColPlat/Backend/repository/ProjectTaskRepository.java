package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    List<ProjectTask> findAllByUserId(Long id);

    List<ProjectTask> findAllByProjectAndUsers_Id(Project project, Long user_id);

    List<ProjectTask> findAllByProjectId(Long projectId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM _project_task_users WHERE project_task_id = :id", nativeQuery = true)
    void deleteTaskUsers(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM _task_note WHERE project_task_id = :id", nativeQuery = true)
    void deleteTaskNotes(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM _project_task WHERE id = :id", nativeQuery = true)
    void deleteTask(@Param("id") Long id);
}
