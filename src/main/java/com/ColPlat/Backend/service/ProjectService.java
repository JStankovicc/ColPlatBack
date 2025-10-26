package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.ProjectCreateRequest;
import com.ColPlat.Backend.model.dto.response.ProjectInfoResponse;
import com.ColPlat.Backend.model.dto.response.ProjectResponse;
import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectService {
    Project getProjectById(long l);

    void save(Project project);

    List<ProjectInfoResponse> getProjectsByUser(User user);

    void addProject(ProjectCreateRequest request, Long companyId);

    List<User> getAllProjectUsers(Long projectId);

    List<Project> getAllProjectsByCompany(Long id);

    ProjectResponse getProjectResponseById(Long id);

    List<ProjectResponse> getAllProjectResponsesByCompany(Long companyId);
}
