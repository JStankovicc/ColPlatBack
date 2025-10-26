package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.ProjectTask;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.repository.ProjectTaskRepository;
import com.ColPlat.Backend.service.ProjectTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectTaskServiceImplementation implements ProjectTaskService {

    private final ProjectTaskRepository projectTaskRepository;

    @Override
    public List<ProjectTask> getUserTasks(Project project, Long id) {
        List<ProjectTask> tasks = projectTaskRepository.findAllByProjectAndUsers_Id(project,id);
        System.out.println("Rezultat: " + tasks.size());
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

}
