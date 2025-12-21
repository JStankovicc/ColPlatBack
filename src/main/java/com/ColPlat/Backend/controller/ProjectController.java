package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.*;
import com.ColPlat.Backend.model.dto.response.ProjectInfoResponse;
import com.ColPlat.Backend.model.dto.response.ProjectResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
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
    private final UserProfileService userProfileService;

    @GetMapping("/tasks/my")
    public ResponseEntity<List<ProjectTask>> getMyTasks(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId){
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(projectTaskService.getUserTasks(projectService.getProjectById(projectId), userService.findByEmail(jwtService.extractUserName(token)).getId()));
    }

    @GetMapping("/tasks/project")
    public ResponseEntity<List<ProjectTask>> getProjectTasks(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId){
        return ResponseEntity.ok(projectTaskService.getTasksByProject(projectId));
    }

    @GetMapping("/taskStatus/getAll")
    public ResponseEntity<List<TaskStatus>> getAllTaskStatuses(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId){

        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(taskStatusService.getAllForProject(1L));
    }

    @DeleteMapping("/deleteTaskStatus")
    public ResponseEntity<String> deleteTaskStatus(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("id") Long id){
        taskStatusService.deleteById(id);
        return ResponseEntity.ok("Uspesno obrisan status za task");
    }

    @PostMapping("/addTaskStatus")
    public ResponseEntity<String> addTaskStatus(@RequestHeader("Authorization") String authorizationHeader, @RequestBody TaskStatusRequest taskStatus){
        taskStatusService.addFromRequest(taskStatus);
        return ResponseEntity.ok("Uspesno dodat status za task");

    }

    @PostMapping("/updateTaskStatus")
    public ResponseEntity<String> updateTaskStatus(@RequestHeader("Authorization") String authorizationHeader, @RequestBody TaskStatus taskStatus){
        taskStatusService.updateTaskStatus(taskStatus);
        return ResponseEntity.ok("Uspesno azurirano");
    }

    @PostMapping("/addUserToProjectTask")
    public ResponseEntity<String> addUserToProjectTask(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("userId") Long userId, @RequestParam("taskId") Long taskId){
        User user = userService.findById(userId);
        ProjectTask task = projectTaskService.getTaskById(taskId);
        if (task == null){
            return ResponseEntity.notFound().build();
        }
        task.getUsers().add(user);
        projectTaskService.save(task);
        return ResponseEntity.ok("Korisnik dodat na task");
    }

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestHeader("Authorization") String authorizationHeader, @RequestBody AddProjectTaskRequest addProjectTaskRequest){

        String token = authorizationHeader.replace("Bearer ", "");

        projectTaskService.createTask(userService.findByEmail(jwtService.extractUserName(token)), addProjectTaskRequest.getProjectTaskName(), addProjectTaskRequest.getProjectId(), addProjectTaskRequest.getStatusID());
        return ResponseEntity.ok("Success");
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

            ProjectTask task = projectTaskService.getTaskById(request.getId());
            if (task == null) {
                return ResponseEntity.notFound().build();
            }

            // Ažuriraj sva polja
            task.setName(request.getName());
            task.setDescription(request.getDescription());
            task.setDateDue(request.getDateDue());
            task.setPriority(request.getPriority());
            task.setStatusId(request.getStatusId());

            projectTaskService.save(task);

            return ResponseEntity.ok("Task uspešno ažuriran");

        } catch (Exception e) {
            System.err.println("Error in updateProjectTask: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Greška pri ažuriranju task-a: " + e.getMessage());
        }
    }

    @DeleteMapping("/tasks/delete")
    @Transactional
    public ResponseEntity<String> deleteProjectTask(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("id") Long id){
        try {
            ProjectTask task = projectTaskService.getTaskById(id);
            if (task == null) {
                return ResponseEntity.notFound().build();
            }
            projectTaskService.deleteById(id);
            return ResponseEntity.ok("Task uspešno obrisan");
        } catch (Exception e) {
            System.err.println("Error in deleteProjectTask: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Greška pri brisanju task-a: " + e.getMessage());
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

    @GetMapping("/getAllProjectUsers")
    public ResponseEntity<List<UserResponse>> getAllProjectUsers(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId){
        List<User> users = projectService.getAllProjectUsers(projectId);
        return ResponseEntity.ok(users.stream().map(user -> userProfileService.getUserResponseFromUser(user)).toList());
    }
}
