package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.UserProfile;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.repository.CompanyRepository;
import com.ColPlat.Backend.repository.UserProfileRepository;
import com.ColPlat.Backend.repository.UserRepository;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class DataLoader {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserProfileRepository userProfileRepository;

    private final CompanyRepository companyRepository;

    @PostConstruct
    public void addUserData(){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ADMIN);
        roles.add(Role.SALES_MANAGEMENT);
        roles.add(Role.SALES);
        roles.add(Role.PROJECT_MANAGEMENT);
        roles.add(Role.PROJECT);
        User user = new User(1L,"j.stankovic001@gmail.com",passwordEncoder.encode("123"),1L,1L,roles,true,true,true, LocalDateTime.now(),LocalDateTime.now());
        userRepository.save(user);


        byte[] defaultProfilePic = null;

        //try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/testSpiderman.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        UserProfile userProfile = new UserProfile(1L,"JStankovic", "Jovan", "Stankovic",defaultProfilePic,1L, LocalDateTime.now(),LocalDateTime.now());
        userProfileRepository.save(userProfile);
    }

    @PostConstruct
    public void addMockCompany(){
        byte[] defaultLogo = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/mockCompanyLogo.png")) {
            if (inputStream != null) {
                defaultLogo = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }
        Company company = new Company(1L,"MockCompany", "123456789", 1L, defaultLogo, 1L, true, "BASIC", LocalDateTime.now(), LocalDateTime.now());

        companyRepository.save(company);

    }

}
