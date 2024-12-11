package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.model.enums.SupportTypes;
import com.ColPlat.Backend.repository.*;
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

    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final LocationRepository locationRepository;

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

        Set<SupportTypes> supportTypes = new HashSet<>();
        supportTypes.add(SupportTypes.EMAIL);
        supportTypes.add(SupportTypes.CHAT);
        //supportTypes.add(SupportTypes.PHONE);

        Company company = new Company(1L,"MockCompany", "123456789", 1L, defaultLogo, 1L, true, supportTypes, 5 , 10 , 20 , LocalDateTime.now(), LocalDateTime.now());

        companyRepository.save(company);

    }

    @PostConstruct
    public void addMockLocation(){
        Country country = new Country((short) 1,"Srbija");
        countryRepository.save(country);

        Region region = new Region(1,(short) 1, "Beograd");
        regionRepository.save(region);
        region = new Region(2, (short) 1, "Raski okrug");
        regionRepository.save(region);

        City city = new City(1,1, "Grad Beograd");
        cityRepository.save(city);
        city = new City(2, 2, "Kraljevo");
        cityRepository.save(city);

        Location location = new Location(1L, 2, "Karadjordjeva 171");
        locationRepository.save(location);
        location = new Location(2L,1,"Narodnih heroja 17/13");
        locationRepository.save(location);

    }

}
