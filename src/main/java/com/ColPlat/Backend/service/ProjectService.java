package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.entity.Project;

public interface ProjectService {
    Project getProjectById(long l);

    void save(Project project);
}
