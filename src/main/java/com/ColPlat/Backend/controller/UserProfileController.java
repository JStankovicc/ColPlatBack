package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.UserProfileWithPasswordRequest;
import com.ColPlat.Backend.model.dto.request.UserProfileWithoutPasswordRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;
import com.ColPlat.Backend.model.dto.response.UserProfileResponse;
import com.ColPlat.Backend.service.AuthenticationService;
import com.ColPlat.Backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/v1/userProfile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final AuthenticationService authenticationService;

    @GetMapping("/getUserProfile")
    public ResponseEntity<UserProfileResponse> getUserProfile(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(userProfileService.getUserProfileFromToken(token));
    }

    @PostMapping("/uploadProfilePic")
    public ResponseEntity<byte[]> uploadProfilePic(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("file") MultipartFile file) {
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            byte[] imageBytes = file.getBytes();
            userProfileService.changeProfilePicture(token, imageBytes);
            return ResponseEntity.ok(imageBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/updateProfileWithoutPassword")
    public ResponseEntity<JwtAuthenticationResponse> updateProfileWithoutPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserProfileWithoutPasswordRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = userProfileService.updateProfileWithoutPassword(token, request);
            return ResponseEntity.ok(jwtAuthenticationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/updateProfileWithPassword")
    public ResponseEntity<JwtAuthenticationResponse> updateProfileWithPassword(@RequestHeader("Authorization") String authorizationHeader, @RequestBody UserProfileWithPasswordRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = userProfileService.updateProfileWithPassword(token, request);
            return ResponseEntity.ok(jwtAuthenticationResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
