package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.ProjectCreateRequest;
import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.ProjectInfoResponse;
import com.ColPlat.Backend.model.dto.response.ProjectResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.Project;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.repository.ProjectRepository;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.ProjectService;
import com.ColPlat.Backend.service.UserProfileService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplementation implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyService companyService;
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Override
    public Project getProjectById(long l) {
        return projectRepository.findById(l).orElse(null);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public List<ProjectInfoResponse> getProjectsByUser(User user) {
        List<Project> projects = projectRepository.findByUsersContaining(user);

        return projects.stream()
                .map(project -> {
                    ProjectInfoResponse response = new ProjectInfoResponse();
                    response.setId(project.getId());
                    response.setName(project.getName());
                    return response;
                })
                .toList();
    }

    @Override
    public void addProject(ProjectCreateRequest request, Long companyId) {
        Project project = Project.builder().name(request.getName()).description(request.getDescription()).startDate(request.getStartDate()).companyId(companyId).build();

        projectRepository.save(project);
    }

    @Override
    public List<User> getAllProjectUsers(Long projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);
        if(project != null){
            return project.getUsers().stream().toList();
        }
        return null;
    }

    @Override
    public List<Project> getAllProjectsByCompany(Long id) {
        return projectRepository.findAllByCompanyId(id);
    }

    @Override
    public ProjectResponse getProjectResponseById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            CompanyResponse companyResponse = companyService.getCompanyResponseFromCompanyId(project.get().getCompanyId());
            Set<User> users = project.get().getUsers();
            Set<UserResponse> userResponses = new HashSet<>();
            for(User u : users){
                userResponses.add(userProfileService.getUserResponseFromUser(u));
            }
            return ProjectResponse.builder()
                    .id(project.get().getId())
                    .name(project.get().getName())
                    .description(project.get().getDescription())
                    .company(companyResponse)
                    .teamId(project.get().getTeamId())
                    .projectTasks(project.get().getProjectTasks())
                    .note(project.get().getNote())
                    .createdAt(project.get().getCreatedAt())
                    .updatedAt(project.get().getUpdatedAt())
                    .startDate(project.get().getStartDate())
                    .users(userResponses)
                    .build();
        }
        return null;

    }

    @Override
    public List<ProjectResponse> getAllProjectResponsesByCompany(Long companyId) {
        List<Project> projects = projectRepository.findAllByCompanyId(companyId);
        List<ProjectResponse> projectResponses = new ArrayList<>();
        for(Project p : projects){
            projectResponses.add(getProjectResponseById(p.getId()));
        }
        return projectResponses;

    }
}
