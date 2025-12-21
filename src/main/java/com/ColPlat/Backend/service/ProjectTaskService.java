package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.ProjectTask;
import com.ColPlat.Backend.model.entity.User;

import java.util.List;

public interface ProjectTaskService {
    List<ProjectTask> getUserTasks(Project project, Long id);

    ProjectTask getTaskById(Long id);

    void save(ProjectTask task);

    List<ProjectTask> getTasksByProject(Long projectId);

    List<User> getTaskUsers(Long taskId);

    void createTask(User user, String projectTaskName, Long projectId, Long statusID);

    void deleteById(Long id);
}
