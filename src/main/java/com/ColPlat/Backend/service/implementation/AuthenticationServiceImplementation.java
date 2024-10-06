package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.SignInRequest;
import com.ColPlat.Backend.model.dto.request.SignInWithTokenRequest;
import com.ColPlat.Backend.model.dto.request.SignUpRequest;
import com.ColPlat.Backend.model.dto.response.JwtAuthenticationResponse;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.repository.UserRepository;
import com.ColPlat.Backend.service.AuthenticationService;
import com.ColPlat.Backend.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword()))
                //.role(Role.USER)
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwt = jwtService.generateToken(user);
        List<String> roles = new ArrayList<String>();
        for(Role role : user.getRole()){
            roles.add(role.toString());
        }
        return JwtAuthenticationResponse.builder().token(jwt).role(roles).build();
    }

    @Override
    public JwtAuthenticationResponse signInWithToken(SignInWithTokenRequest request){
        String token = request.getToken();
        if(jwtService.isTokenValidForRefresh(token)){
            String username = jwtService.extractUserName(token);
            var user = userRepository.findByEmail(username).orElseThrow(() -> new IllegalArgumentException("Invalid email"));
            String jwt = jwtService.generateToken(user);
            List<String> roles = new ArrayList<String>();
            for(Role role : user.getRole()){
                roles.add(role.toString());
            }
            return JwtAuthenticationResponse.builder().token(jwt).role(roles).build();
        }
        else return JwtAuthenticationResponse.builder().token("FALSE").build();
    }
}
