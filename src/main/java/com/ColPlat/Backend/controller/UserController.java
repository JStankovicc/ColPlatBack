package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.UserRequest;
import com.ColPlat.Backend.model.entity.User;
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

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public void addUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
    }

    @DeleteMapping("/delete")
    public void deleteUser(@RequestParam("email") String email){
        userService.deleteUserByEmail(email);
    }
}