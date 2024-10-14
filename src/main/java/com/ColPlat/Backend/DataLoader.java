package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<Role> roles = new ArrayList<>(Arrays.asList(Role.ADMIN, Role.SALES_MANAGEMENT, Role.SALES, Role.PROJECT_MANAGEMENT, Role.PROJECT));
        User user = new User(1,"Jovan","Stankovic","j.stankovic001@gmail.com",passwordEncoder.encode("123"),roles);
        userRepository.save(user);
    }

}
