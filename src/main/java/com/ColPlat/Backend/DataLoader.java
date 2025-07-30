package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.model.enums.EventPriority;
import com.ColPlat.Backend.model.enums.ParticipationStatus;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.model.enums.SupportType;
import com.ColPlat.Backend.repository.*;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


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
    
    // Dodati repository-je za kalendarske funkcionalnosti
    private final CalendarEventRepository calendarEventRepository;
    private final EventParticipantRepository eventParticipantRepository;

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

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
        //try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/testSpiderman.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        UserProfile userProfile = new UserProfile(1L,"Jovan Stankovic", "Jovan", "Stankovic",defaultProfilePic,1L, LocalDateTime.now(),LocalDateTime.now());
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

        Set<SupportType> supportTypes = new HashSet<>();
        supportTypes.add(SupportType.EMAIL);
        supportTypes.add(SupportType.CHAT);
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

    @PostConstruct
    public void addMockCalendarEvents(){
        LocalDateTime now = LocalDateTime.now();
        
        // Event 1 - Sastanak sa klijentom
        CalendarEvent event1 = CalendarEvent.builder()
            .id(1L)
            .title("Sastanak sa klijentom ABC")
            .description("Prezentacija novog proizvoda i diskusija o saradnji")
            .startDateTime(now.plusDays(1).withHour(10).withMinute(0))
            .endDateTime(now.plusDays(1).withHour(11).withMinute(30))
            .priority(EventPriority.HIGH)
            .createdByUserId(1L)
            .teamId(1L)
            .createdAt(now)
            .build();
        calendarEventRepository.save(event1);

        // Event 2 - Tim meeting
        CalendarEvent event2 = CalendarEvent.builder()
            .id(2L)
            .title("Nedeljni tim meeting")
            .description("Pregled projekata i planiranje za sledeću nedelju")
            .startDateTime(now.plusDays(2).withHour(14).withMinute(0))
            .endDateTime(now.plusDays(2).withHour(15).withMinute(0))
            .priority(EventPriority.NORMAL)
            .createdByUserId(1L)
            .teamId(1L)
            .createdAt(now)
            .build();
        calendarEventRepository.save(event2);

        // Event 3 - Obuka zaposlenih
        CalendarEvent event3 = CalendarEvent.builder()
            .id(3L)
            .title("Obuka - Nova tehnologija")
            .description("Uvođenje u korišćenje nove tehnologije u projektu")
            .startDateTime(now.plusDays(3).withHour(9).withMinute(0))
            .endDateTime(now.plusDays(3).withHour(12).withMinute(0))
            .priority(EventPriority.NORMAL)
            .createdByUserId(1L)
            .teamId(1L)
            .createdAt(now)
            .build();
        calendarEventRepository.save(event3);

        // Event 4 - Projektni deadline
        CalendarEvent event4 = CalendarEvent.builder()
            .id(4L)
            .title("Deadline - Projekt XYZ")
            .description("Finalizacija i predaja projekta XYZ")
            .startDateTime(now.plusDays(5).withHour(16).withMinute(0))
            .endDateTime(now.plusDays(5).withHour(17).withMinute(0))
            .priority(EventPriority.HIGH)
            .createdByUserId(1L)
            .teamId(1L)
            .createdAt(now)
            .build();
        calendarEventRepository.save(event4);

        // Event 5 - Lični event
        CalendarEvent event5 = CalendarEvent.builder()
            .id(5L)
            .title("Privatni sastanak")
            .description("Lični termin")
            .startDateTime(now.plusDays(4).withHour(18).withMinute(0))
            .endDateTime(now.plusDays(4).withHour(19).withMinute(0))
            .priority(EventPriority.LOW)
            .createdByUserId(1L)
            .teamId(null) // Nema tim - lični event
            .createdAt(now)
            .build();
        calendarEventRepository.save(event5);

        // Event 6 - Prošli event
        CalendarEvent event6 = CalendarEvent.builder()
            .id(6L)
            .title("Završen sastanak")
            .description("Sastanak koji je već održan")
            .startDateTime(now.minusDays(2).withHour(10).withMinute(0))
            .endDateTime(now.minusDays(2).withHour(11).withMinute(0))
            .priority(EventPriority.NORMAL)
            .createdByUserId(1L)
            .teamId(1L)
            .createdAt(now.minusDays(3))
            .build();
        calendarEventRepository.save(event6);
    }

    @PostConstruct
    public void addMockEventParticipants(){
        // Prvo učitavamo event-ove iz baze
        CalendarEvent event1 = calendarEventRepository.findById(1L).orElse(null);
        CalendarEvent event2 = calendarEventRepository.findById(2L).orElse(null);
        CalendarEvent event3 = calendarEventRepository.findById(3L).orElse(null);
        CalendarEvent event4 = calendarEventRepository.findById(4L).orElse(null);
        CalendarEvent event5 = calendarEventRepository.findById(5L).orElse(null);
        CalendarEvent event6 = calendarEventRepository.findById(6L).orElse(null);

        // Dodavanje učesnika za event 1
        if (event1 != null) {
            EventParticipant participant1 = EventParticipant.builder()
                    .id(1L)
                    .event(event1)  // Koristimo event objekat umesto eventId
                    .userId(1L)
                    .status(ParticipationStatus.ACCEPTED)
                    .build();
            eventParticipantRepository.save(participant1);
        }

        // Dodavanje učesnika za event 2
        if (event2 != null) {
            EventParticipant participant2 = EventParticipant.builder()
                    .id(2L)
                    .event(event2)
                    .userId(1L)
                    .status(ParticipationStatus.ACCEPTED)
                    .build();
            eventParticipantRepository.save(participant2);
        }

        // Dodavanje učesnika za event 3
        if (event3 != null) {
            EventParticipant participant3 = EventParticipant.builder()
                    .id(3L)
                    .event(event3)
                    .userId(1L)
                    .status(ParticipationStatus.NO_RESPONSE)
                    .build();
            eventParticipantRepository.save(participant3);
        }

        // Dodavanje učesnika za event 4
        if (event4 != null) {
            EventParticipant participant4 = EventParticipant.builder()
                    .id(4L)
                    .event(event4)
                    .userId(1L)
                    .status(ParticipationStatus.ACCEPTED)
                    .build();
            eventParticipantRepository.save(participant4);
        }

        // Dodavanje učesnika za event 5 (lični event)
        if (event5 != null) {
            EventParticipant participant5 = EventParticipant.builder()
                    .id(5L)
                    .event(event5)
                    .userId(1L)
                    .status(ParticipationStatus.ACCEPTED)
                    .build();
            eventParticipantRepository.save(participant5);
        }

        // Dodavanje učesnika za event 6 (prošli event)
        if (event6 != null) {
            EventParticipant participant6 = EventParticipant.builder()
                    .id(6L)
                    .event(event6)
                    .userId(1L)
                    .status(ParticipationStatus.ACCEPTED)
                    .build();
            eventParticipantRepository.save(participant6);
        }
    }
}