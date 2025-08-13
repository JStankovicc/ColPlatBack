package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.TaskStatus;

import java.util.List;

public interface TaskStatusService {
    List<TaskStatus> getAllForProject(Long id);
}
