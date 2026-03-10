package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.ChangeUserRoleRequest;
import com.ColPlat.Backend.model.dto.request.UserRequest;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.UserProfileService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final CompanyService companyService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public void addUser(@RequestHeader("Authorization") String authorizationHeader,@RequestBody UserRequest userRequest){
        String token = authorizationHeader.replace("Bearer ", "");
        Company company = companyService.getCompanyFromToken(token);
        userProfileService.createUserAndProfile(userRequest, company);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam("email") String email){
        userService.deleteUserByEmail(email);
    }

    @PutMapping("/changeRole")
    public void changeRole(@RequestHeader("Authorization") String authorizationHeader, @RequestBody ChangeUserRoleRequest changeUserRoleRequest){
        userService.changeRole(changeUserRoleRequest);
    }
}