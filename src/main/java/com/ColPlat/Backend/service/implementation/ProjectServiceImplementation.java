package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.repository.ProjectRepository;
import com.ColPlat.Backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project getProjectById(long l) {
        return projectRepository.findById(l).orElse(null);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }
}
