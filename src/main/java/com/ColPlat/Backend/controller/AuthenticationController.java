package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.SignInRequest;
import com.ColPlat.Backend.model.dto.request.SignInWithTokenRequest;
import com.ColPlat.Backend.model.dto.request.SignUpRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;
import com.ColPlat.Backend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/token")
    public ResponseEntity<JwtAuthenticationResponse> signInWithToken(@RequestBody SignInWithTokenRequest request){
        JwtAuthenticationResponse response = authenticationService.signInWithToken(request);
        if(response.getToken().equals("FALSE")){
            return ResponseEntity.status(403).build();
        }else return ResponseEntity.ok(response);
    }
}