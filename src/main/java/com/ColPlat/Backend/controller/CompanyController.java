package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;
import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.UserProfile;
import com.ColPlat.Backend.model.enums.SupportType;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final ProjectService projectService;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final JwtService jwtService;
    private final ProjectTaskService projectTaskService;

    @GetMapping("/getCompanyInfo")
    public ResponseEntity<CompanyResponse> getCompanyInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(companyService.getCompanyInfoFromToken(token));
    }

    @GetMapping("/getCompanySettingsInfo")
    public ResponseEntity<CompanySettingsInfoResponse> getCompanySettingsInfo(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ","");
        return ResponseEntity.ok(companyService.getCompanySettingsInfoFromToken(token));
    }

    @PostMapping("/uploadLogo")
    public ResponseEntity<byte[]> setLogoPic(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("file") MultipartFile file){
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            byte[] imageBytes = file.getBytes();
            String username = jwtService.extractUserName(token);
            User user = userService.findByEmail(username);
            Company company = companyService.findById(user.getCompanyId());
            companyService.replaceLogo(company, imageBytes);
            return ResponseEntity.ok(imageBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAllSupportTypes")
    public ResponseEntity<List<String>> getAllSupportTypes(){
        SupportType[] supportTypes = SupportType.values();
        List<String> types = new ArrayList<>();
        for(SupportType s : supportTypes){
            types.add(s.toString());
        }
        return ResponseEntity.ok(types);
    }

    @GetMapping("/getAllCompanyProjectWorkersNotOnProject")
    public ResponseEntity<List<UserResponse>> getAllCompanyProjectWorkersNotOnProject(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId){

        String token = authorizationHeader.replace("Bearer ", "");

        Company company = companyService.getCompanyFromToken(token);

        List<User> users = projectService.getAllProjectUsers(projectId);

        List<User> allUsers = userService.findAllByCompany(company.getId());

        for (User user : users){
            allUsers.removeIf(allUser -> Objects.equals(user.getId(), allUser.getId()));
        }

        List<UserResponse> userResponses = new ArrayList<>();

        for (User user : allUsers){
            userResponses.add(userProfileService.getUserResponseFromUser(user));
        }

        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/getAllCompanyProjectWorkersNotOnTask")
    public ResponseEntity<List<UserResponse>> getAllCompanyProjectWorkersNotOnTask(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId, @RequestParam("taskId") Long taskId){

        List<User> users = projectService.getAllProjectUsers(projectId);

        List<User> taskUsers = projectTaskService.getTaskUsers(taskId);

        users.removeIf(taskUsers::contains);

        return ResponseEntity.ok(users.stream().map(user -> userProfileService.getUserResponseFromUser(user)).toList());
    }

    @GetMapping("/getAllCompanyProjectWorkersOnTask")
    public ResponseEntity<List<UserResponse>> getAllCompanyProjectWorkersOnTask(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("projectId") Long projectId, @RequestParam("taskId") Long taskId){

        List<User> taskUsers = projectTaskService.getTaskUsers(taskId);


        return ResponseEntity.ok(taskUsers.stream().map(user -> userProfileService.getUserResponseFromUser(user)).toList());
    }
}
