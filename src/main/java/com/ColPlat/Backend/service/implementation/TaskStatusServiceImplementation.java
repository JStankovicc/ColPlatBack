package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.TaskStatusRequest;
import com.ColPlat.Backend.model.entity.TaskStatus;
import com.ColPlat.Backend.repository.TaskStatusRepository;
import com.ColPlat.Backend.service.TaskStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskStatusServiceImplementation implements TaskStatusService {

    private final TaskStatusRepository taskStatusRepository;

    @Override
    public List<TaskStatus> getAllForProject(Long id) {
        return taskStatusRepository.findAllByProjectId(id);
    }

    @Override
    public void deleteById(Long id) {
        taskStatusRepository.deleteById(id);
    }

    @Override
    public void addFromRequest(TaskStatusRequest taskStatus) {
        TaskStatus status =  new TaskStatus().builder()
                .name(taskStatus.getName())
                .projectId(taskStatus.getProjectId())
                .build();
        taskStatusRepository.save(status);
    }

    @Override
    public void updateTaskStatus(TaskStatus taskStatus) {
        taskStatusRepository.save(taskStatus);
    }
}
