package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.ProjectTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectTaskRepository extends JpaRepository<ProjectTask, Long> {
    List<ProjectTask> findAllByUserId(Long id);

    List<ProjectTask> findAllByProjectAndUsers_Id(Project project, Long user_id);

    List<ProjectTask> findAllByProjectId(Long projectId);
}
