package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.ProjectTask;

import java.util.List;

public interface ProjectTaskService {
    List<ProjectTask> getUserTasks(Project project, Long id);

    ProjectTask getTaskById(Long id);

    void save(ProjectTask task);

}
