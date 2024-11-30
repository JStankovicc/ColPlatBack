package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/userProfile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping("/getUserProfile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(userProfileService.getUserProfileFromToken(token));
    }
}
