package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public DataLoader(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void addUserData(){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        roles.add(Role.SALES_MANAGEMENT);
        roles.add(Role.SALES);
        roles.add(Role.PROJECT_MANAGEMENT);
        roles.add(Role.PROJECT);
        User user = new User(1L,"Jovan","Stankovic","j.stankovic001@gmail.com",passwordEncoder.encode("123"),1L,1L,roles,true,true,true, LocalDateTime.now(),LocalDateTime.now());
        userRepository.save(user);
    }

}
