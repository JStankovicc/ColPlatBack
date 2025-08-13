package com.ColPlat.Backend.service.implementation;

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
}
