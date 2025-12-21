package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.ProjectTask;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.TaskPriority;
import com.ColPlat.Backend.repository.ProjectTaskRepository;
import com.ColPlat.Backend.repository.TaskNoteRepository;
import com.ColPlat.Backend.service.ProjectService;
import com.ColPlat.Backend.service.ProjectTaskService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImplementation implements ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;
    private final ProjectService projectService;
    private final TaskNoteRepository taskNoteRepository;
    private final EntityManager entityManager;

    @Override
    public List<ProjectTask> getUserTasks(Project project, Long id) {
        List<ProjectTask> tasks = projectTaskRepository.findAllByProjectAndUsers_Id(project,id);
        return tasks;
    }

    @Override
    public ProjectTask getTaskById(Long id) {
        return projectTaskRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProjectTask task) {
        projectTaskRepository.save(task);
    }

    @Override
    public List<ProjectTask> getTasksByProject(Long projectId) {
        return projectTaskRepository.findAllByProjectId(projectId);
    }

    @Override
    public List<User> getTaskUsers(Long taskId) {
        ProjectTask task = projectTaskRepository.findById(taskId).orElse(null);
        return task.getUsers().stream().toList();
    }

    @Override
    public void createTask(User user, String projectTaskName, Long projectId, Long statusID) {
        new ProjectTask();
        ProjectTask projectTask = ProjectTask.builder()
                .name(projectTaskName)
                .project(projectService.getProjectById(projectId))
                .statusId(statusID)
                .priority(TaskPriority.NORMAL)
                .users(Set.of(user))
                .user(user)
                .build();

        projectTaskRepository.save(projectTask);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {

        projectTaskRepository.deleteTaskUsers(id);
        projectTaskRepository.deleteTaskNotes(id);
        projectTaskRepository.deleteTask(id);

    }

}
