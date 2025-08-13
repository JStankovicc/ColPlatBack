package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.ProjectTask;
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
    public List<ProjectTask> getUserTasks(Long id) {
        return projectTaskRepository.findAllByUserId(id);
    }

    @Override
    public ProjectTask getTaskById(Long id) {
        return projectTaskRepository.findById(id).orElse(null);
    }

    @Override
    public void save(ProjectTask task) {
        projectTaskRepository.save(task);
    }
}
