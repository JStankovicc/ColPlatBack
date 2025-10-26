package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.TaskStatusRequest;
import com.ColPlat.Backend.model.entity.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    List<TaskStatus> getAllForProject(Long id);

    void deleteById(Long id);

    void addFromRequest(TaskStatusRequest taskStatus);

    void updateTaskStatus(TaskStatus taskStatus);
}
