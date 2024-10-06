package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.UserRequest;
import com.ColPlat.Backend.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();

    List<User> findAll();

    void addUser(UserRequest userRequest);

    void deleteUserByEmail(String email);

    User findByEmail(String zapisnikUneo);
}