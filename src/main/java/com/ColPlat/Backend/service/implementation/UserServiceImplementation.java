package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.UserRequest;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.repository.UserRepository;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(UserRequest userRequest) {

        String password = userRequest.getPassword();

        User user = User.builder().firstName(userRequest.getFirstName()).lastName(userRequest.getLastName()).email(userRequest.getEmail()).password(new BCryptPasswordEncoder().encode(password)).build();

        for (String roleStr : userRequest.getRole()) {
            try {
                Role roleEnum = Role.valueOf(roleStr.toUpperCase());
                user.getRole().add(roleEnum);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid role: " + roleStr);
            }
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        userRepository.delete(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
