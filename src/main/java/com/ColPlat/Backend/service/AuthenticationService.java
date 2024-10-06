package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.SignInRequest;
import com.ColPlat.Backend.model.dto.request.SignInWithTokenRequest;
import com.ColPlat.Backend.model.dto.request.SignUpRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);

    JwtAuthenticationResponse signInWithToken(SignInWithTokenRequest request);
}