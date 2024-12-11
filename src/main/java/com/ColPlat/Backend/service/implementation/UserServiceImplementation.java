package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.UserRequest;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.repository.UserRepository;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userRequest.getPassword());

        User user = User.builder()
                .email(userRequest.getEmail())
                .password(encodedPassword)
                .roles(new HashSet<>())
                .build();

        for (String roleStr : userRequest.getRole()) {
            try {
                Role roleEnum = Role.valueOf(roleStr.toUpperCase());
                user.getRoles().add(roleEnum);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid role: " + roleStr);
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

    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + userEmail));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

}
