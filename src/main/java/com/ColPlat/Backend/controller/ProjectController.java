package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.ProjectCreateRequest;
import com.ColPlat.Backend.model.dto.request.ProjectNoteUpdateRequest;
import com.ColPlat.Backend.model.dto.request.ProjectTaskUpdateRequest;
import com.ColPlat.Backend.model.dto.response.ProjectInfoResponse;
import com.ColPlat.Backend.model.dto.response.ProjectResponse;
import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProjectController {

    private final ProjectTaskService projectTaskService;
    private final TaskStatusService taskStatusService;
    private final UserService userService;
    private final ProjectService projectService;
    private final CompanyService companyService;
    private final JwtService jwtService;

    @GetMapping("/tasks/my")
    public ResponseEntity<List<ProjectTask>> getMyTasks(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(projectTaskService.getUserTasks(userService.findByEmail(jwtService.extractUserName(token)).getId()));
    }

    @GetMapping("/taskStatus/getAll")
    public ResponseEntity<List<TaskStatus>> getAllTaskStatuses(@RequestHeader("Authorization") String authorizationHeader){

        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(taskStatusService.getAllForProject(1L));
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<String> addProject(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ProjectCreateRequest projectCreateRequest){
        String token = authorizationHeader.replace("Bearer ", "");
        Company company = companyService.getCompanyFromToken(token);
        projectService.addProject(projectCreateRequest, company.getId());
        return ResponseEntity.ok("Success");
    }

    @GetMapping("/allByCompany")
    public ResponseEntity<List<ProjectResponse>> getAllProjectsByCompany(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        Company company = companyService.getCompanyFromToken(token);
        return ResponseEntity.ok(projectService.getAllProjectResponsesByCompany(company.getId()));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectInfoResponse>> getProjectList(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(projectService.getProjectsByUser(userService.findByEmail(jwtService.extractUserName(token))));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectInfo(
            @PathVariable Long projectId,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        Project project = projectService.getProjectById(projectId);

        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(project);
    }

    @PutMapping("/updateNote")
    public ResponseEntity<String> updateProjectNote(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ProjectNoteUpdateRequest request) {

        try {
            String token = authorizationHeader.replace("Bearer ", "");
            System.out.println("=== updateProjectNote called ===");
            System.out.println("Project ID: " + request.getId());
            System.out.println("New note: " + request.getNote());

            Project project = projectService.getProjectById(request.getId());
            if (project == null) {
                return ResponseEntity.notFound().build();
            }

            project.setNote(request.getNote());
            projectService.save(project);

            return ResponseEntity.ok("Beleška uspešno ažurirana");

        } catch (Exception e) {
            System.err.println("Error in updateProjectNote: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Greška pri ažuriranju beleške: " + e.getMessage());
        }
    }

    @PutMapping("/tasks/update")
    public ResponseEntity<String> updateProjectTask(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody ProjectTaskUpdateRequest request) {

        try {
            String token = authorizationHeader.replace("Bearer ", "");
            System.out.println("=== updateProjectTask called ===");
            System.out.println("Task ID: " + request.getId());
            System.out.println("New status: " + request.getStatus());

            ProjectTask task = projectTaskService.getTaskById(request.getId());
            if (task == null) {
                return ResponseEntity.notFound().build();
            }

            // Ažuriraj sva polja
            task.setName(request.getName());
            task.setDescription(request.getDescription());
            task.setDateDue(request.getDateDue());
            task.setPriority(request.getPriority());
            task.setStatus(request.getStatus());

            projectTaskService.save(task);

            return ResponseEntity.ok("Task uspešno ažuriran");

        } catch (Exception e) {
            System.err.println("Error in updateProjectTask: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Greška pri ažuriranju task-a: " + e.getMessage());
        }
    }

    @PostMapping("/addUserToProject")
    public ResponseEntity<String> addUserToProject(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("userId") Long userId, @RequestParam("projectId") Long projectId){
        User user = userService.findById(userId);
        Project project = projectService.getProjectById(projectId);
        if (project == null){
            return ResponseEntity.notFound().build();
        }
        project.getUsers().add(user);
        projectService.save(project);
        return ResponseEntity.ok("Korisnik dodat na projekat");
    }
}
