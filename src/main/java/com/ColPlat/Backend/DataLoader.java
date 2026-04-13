package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.model.enums.*;
import com.ColPlat.Backend.repository.*;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class DataLoader {
/**
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final CompanyRepository companyRepository;
    private final CountryRepository countryRepository;
    private final RegionRepository regionRepository;
    private final CityRepository cityRepository;
    private final LocationRepository locationRepository;
    private final DepartmentRepository departmentRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final TaskNoteRepository taskNoteRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final MovableAssetRepository movableAssetRepository;

    private final CalendarEventRepository calendarEventRepository;
    private final EventParticipantRepository eventParticipantRepository;

    private final ContactRepository contactRepository;
    private final ContactListRepository contactListRepository;

    //@PostConstruct
    @Transactional
    public void executeDataLoader() {
        addUserData();
        addMoreMockUsers();

        addMockCompany();

        addPavleNedeljkovic();
        addDusanZekovic();
        addDarkoRosic();
        addMarinaSejnjanovic();
        addGoranSejnjanovic();
        addNenadNedeljkovic();
        addNenadCendic();
        addSlobodanGrkovic();
        addResatRedza();
        addIvanMiletic();
        
        //addMockDepartmentsAndTeams();

        //addMockProjectTaskStatusData();
        //addMockProjectData();

        addMockLocation();

        //addMockCalendarEvents();
        //addMockEventParticipants();

       // addMockContacts();
        //addMockContactsLists();

        //addMockMovableAssetsData();
    }

    public void addUserData(){
        Set<Role> rolesAdmin = new HashSet<>();
        rolesAdmin.add(Role.ADMIN);
        rolesAdmin.add(Role.SALES_MANAGEMENT);
        rolesAdmin.add(Role.SALES);
        rolesAdmin.add(Role.PROJECT_MANAGEMENT);
        rolesAdmin.add(Role.PROJECT);
        User user1 = new User(1L,"jovan.stankovic@abplat.com",passwordEncoder.encode("HWDRTWWU123"),1L,1L,rolesAdmin,true,true,true, LocalDateTime.now(),LocalDateTime.now());
        userRepository.save(user1);

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

        UserProfile userProfile1 = new UserProfile(1L,"Jovan Stankovic", "Jovan", "Stankovic",defaultProfilePic,1L, LocalDateTime.now(),LocalDateTime.now());
        userProfileRepository.save(userProfile1);
    }

    public void addMoreMockUsers() {
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        // Dodavanje Koste Markovica
        Set<Role> rolesKosta = new HashSet<>();
        rolesKosta.add(Role.PROJECT_MANAGEMENT);
        rolesKosta.add(Role.PROJECT);
        rolesKosta.add(Role.SALES);
        rolesKosta.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile2 = new UserProfile(2L, "Kosta Markovic", "Kosta", "Markovic", defaultProfilePic, 1L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile2);

        User user2 = new User(2L, "kosta.markovic@abplat.com", passwordEncoder.encode("123KMarkovic123"), 1L, userProfile2.getId(), rolesKosta, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user2);

    }

    public void addPavleNedeljkovic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.ADMIN);
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(3L, "Pavle Nedeljković", "Pavle", "Nedeljkovic", defaultProfilePic, 3L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(3L, "pavle.nedeljkovic@lorapro.rs", passwordEncoder.encode("pavlelorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addDusanZekovic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(4L, "Dušan Zeković", "Dušan", "Zekovic", defaultProfilePic, 4L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(4L, "dusan.zekovic@lorapro.rs", passwordEncoder.encode("dusanlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addDarkoRosic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(5L, "Darko Rosić", "Darko", "Rosić", defaultProfilePic, 5L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(5L, "darko.rosic@lorapro.rs", passwordEncoder.encode("darkolorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addMarinaSejnjanovic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(6L, "Marina Sejnjanović", "Marina", "Sejnjanović", defaultProfilePic, 6L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(6L, "marina.sejnjanovic@lorapro.rs", passwordEncoder.encode("marinalorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addGoranSejnjanovic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(7L, "Goran Sejnjanović", "Goran", "Sejnjanović", defaultProfilePic, 7L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(7L, "goran.sejnjanović@lorapro.rs", passwordEncoder.encode("goranlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addNenadNedeljkovic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.ADMIN);
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(8L, "Nenad Nedeljkovic", "Nenad", "Nedeljkovic", defaultProfilePic, 8L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(8L, "nenad.nedeljkovic@lorapro.rs", passwordEncoder.encode("nenadlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addNenadCendic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(9L, "Nenad Ćendić", "Nenad", "Ćendić", defaultProfilePic, 9L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(9L, "nenad.cendic@lorapro.rs", passwordEncoder.encode("nenadlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addSlobodanGrkovic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(10L, "Slobodan Grković", "Slobodan", "Grković", defaultProfilePic, 10L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(10L, "slobodan.grkovic@lorapro.rs", passwordEncoder.encode("slobodanlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addResatRedza(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(11L, "Resat Redža", "Resat", "Redža", defaultProfilePic, 11L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(11L, "resat.redza@lorapro.rs", passwordEncoder.encode("resatlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addIvanMiletic(){
        byte[] defaultProfilePic = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/default_profile_picture.png")) {
            if (inputStream != null) {
                defaultProfilePic = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        Set<Role> rolesPavle = new HashSet<>();
        rolesPavle.add(Role.PROJECT_MANAGEMENT);
        rolesPavle.add(Role.PROJECT);
        rolesPavle.add(Role.SALES);
        rolesPavle.add(Role.SALES_MANAGEMENT);

        UserProfile userProfile3 = new UserProfile(12L, "Ivan Miletić", "Ivan", "Miletić", defaultProfilePic, 12L, LocalDateTime.now(), LocalDateTime.now());
        userProfileRepository.save(userProfile3);

        User user3 = new User(12L, "ivan.miletic@lorapro.rs", passwordEncoder.encode("ivanlorapro"), 2L, userProfile3.getId(), rolesPavle, true, true, true, LocalDateTime.now(), LocalDateTime.now());
        userRepository.save(user3);
    }

    public void addMockDepartmentsAndTeams() {
        // Kreiranje Department-a
        Department salesDepartment = new Department();
        salesDepartment.setId(1L);
        salesDepartment.setDepartmentType(DepartmentType.SALES);
        salesDepartment.setCompanyId(1L);
        departmentRepository.save(salesDepartment);

        Department developmentDepartment = new Department();
        developmentDepartment.setId(2L);
        developmentDepartment.setDepartmentType(DepartmentType.PROJECT);
        developmentDepartment.setCompanyId(1L);
        departmentRepository.save(developmentDepartment);

        // Kreiranje Timova
        Team salesTeam = new Team();
        salesTeam.setName("Sales Team A");
        salesTeam.setDescription("Tim zadužen za akviziciju novih klijenata");
        salesTeam.setCompanyId(1L);
        salesTeam.setDepartment(salesDepartment);
        salesTeam.setUserIds(new HashSet<>(Arrays.asList(1L, 2L))); // Jovan Stankovic (1L) i Nikola Milovanovic (3L)
        teamRepository.save(salesTeam);

        Team devTeam = new Team();
        devTeam.setName("Development Team B");
        devTeam.setDescription("Tim zadužen za razvoj i održavanje glavnog proizvoda");
        devTeam.setCompanyId(1L);
        devTeam.setDepartment(developmentDepartment);
        devTeam.setUserIds(new HashSet<>(Arrays.asList(3L))); // Kosta Markovic (2L)
        teamRepository.save(devTeam);

        Team devTeamAlpha = new Team();
        devTeamAlpha.setName("Development Team Alpha");
        devTeamAlpha.setDescription("Tim zadužen za istraživanje i razvoj (R&D)");
        devTeamAlpha.setCompanyId(1L);
        devTeamAlpha.setDepartment(developmentDepartment);
        devTeamAlpha.setUserIds(new HashSet<>()); // Prazan tim za sada
        teamRepository.save(devTeamAlpha);
    }

    public void addMockCompany(){
        byte[] defaultLogo = null;
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/ABPlat Logo Inline.png")) {
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

        Company company = new Company(1L,"ABPlat", "123456789", 1L, defaultLogo, 1L, true, supportTypes, 20 , LocalDateTime.now(), LocalDateTime.now());

        companyRepository.save(company);

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("images/loraPro.png")) {
            if (inputStream != null) {
                defaultLogo = inputStream.readAllBytes();
            } else {
                throw new RuntimeException("Slika nije pronađena u resources folderu!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Greška prilikom učitavanja slike!");
        }

        supportTypes.add(SupportType.PHONE);

        Company company2 = new Company(2L,"Lora-Pro DOO", "21426300", 2L, defaultLogo, 2L, true, supportTypes, 20 , LocalDateTime.now(), LocalDateTime.now());

        companyRepository.save(company2);
    }

    public void addMockLocation(){
        Country country = new Country((short) 1,"Srbija");
        countryRepository.save(country);

        Region region = new Region(1,(short) 1, "Beograd");
        regionRepository.save(region);
        region = new Region(3, (short) 1, "Zapadna i Centralna Srbija");
        regionRepository.save(region);

        City city = new City(1, 1, "Beograd");
        cityRepository.save(city);
        city = new City(74, 15, "Kraljevo");
        cityRepository.save(city);

        Location location = new Location(1L, (short) 1, 3, 15, "Kraljevo", "Karadjordjeva 171");
        locationRepository.save(location);
        location = new Location(2L, (short) 1, 1, 1, "Grad Beograd", "Studentska 5");
        locationRepository.save(location);
    }

    public void addMockCalendarEvents(){
        LocalDateTime now = LocalDateTime.now();

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

    public void addMockContactsLists() {
        // Create mock contacts lists
        ContactsList list1 = ContactsList.builder()
                .id(1L)
                .name("Potencijalni klijenti")
                .description("Lista potencijalnih klijenata za kontaktiranje")
                .companyId(1L) // MockCompany
                .teamId(1L)    // Assuming team ID 1 exists
                .countryId((short) 1) // Srbija
                .regionId(1)  // Beograd
                .cityId(1)    // Beograd
                .contacts(new ArrayList<>())
                .status(ContactsListStatus.LEAD)
                .build();
        contactListRepository.save(list1);

        ContactsList list2 = ContactsList.builder()
                .id(2L)
                .name("Aktivni klijenti")
                .description("Lista trenutno aktivnih klijenata")
                .companyId(1L) // MockCompany
                .teamId(1L)    // Assuming team ID 1 exists
                .countryId((short) 1) // Srbija
                .regionId(3)  // Zapadna i Centralna Srbija (Raški okrug)
                .cityId(74)   // Kraljevo
                .contacts(new ArrayList<>())
                .status(ContactsListStatus.CLIENT)
                .build();
        contactListRepository.save(list2);

        ContactsList list3 = ContactsList.builder()
                .id(3L)
                .name("Arhivirani klijenti")
                .description("Lista klijenata sa kojima trenutno ne sarađujemo")
                .companyId(1L) // MockCompany
                .teamId(1L)    // Assuming team ID 1 exists
                .countryId((short) 1) // Srbija
                .regionId(1)  // Beograd
                .cityId(1)    // Beograd
                .contacts(new ArrayList<>())
                .status(ContactsListStatus.LEAD)
                .build();
        contactListRepository.save(list3);
    }

    public void addMockContacts() {
        // Get contacts lists from repository
        ContactsList list1 = contactListRepository.findById(1L).orElse(null);
        ContactsList list2 = contactListRepository.findById(2L).orElse(null);
        ContactsList list3 = contactListRepository.findById(3L).orElse(null);

        if (list1 != null) {
            // Add contacts to "Potencijalni klijenti" list
            Contact contact1 = Contact.builder()
                    .id(1L)
                    .name("Marko Petrović")
                    .companyName("Tech Solutions d.o.o.")
                    .phoneNumber("+381601234567")
                    .email("marko.petrovic@techsolutions.rs")
                    .contactsList(list1)
                    .status(ContactStatus.NEW)
                    .build();
            contactRepository.save(contact1);

            Contact contact2 = Contact.builder()
                    .id(2L)
                    .name("Ana Jovanović")
                    .companyName("Digital Media Group")
                    .phoneNumber("+381642345678")
                    .email("ana.jovanovic@dmg.com")
                    .contactsList(list1)
                    .status(ContactStatus.CONTACTED)
                    .build();
            contactRepository.save(contact2);
        }

        if (list2 != null) {
            // Add contacts to "Aktivni klijenti" list
            Contact contact3 = Contact.builder()
                    .id(3L)
                    .name("Nikola Đorđević")
                    .companyName("Smart Systems")
                    .phoneNumber("+381653456789")
                    .email("nikola.djordjevic@smartsystems.rs")
                    .contactsList(list2)
                    .status(ContactStatus.OFFERED)
                    .build();
            contactRepository.save(contact3);

            Contact contact4 = Contact.builder()
                    .id(4L)
                    .name("Jelena Nikolić")
                    .companyName("Inovation Hub")
                    .phoneNumber("+381664567890")
                    .email("jelena.nikolic@ihub.rs")
                    .contactsList(list2)
                    .status(ContactStatus.CLOSED)
                    .build();
            contactRepository.save(contact4);
        }

        if (list3 != null) {
            // Add contacts to "Arhivirani klijenti" list
            Contact contact5 = Contact.builder()
                    .id(5L)
                    .name("Milan Stanković")
                    .companyName("Old Tech Ltd.")
                    .phoneNumber("+381675678901")
                    .email("milan.stankovic@oldtech.com")
                    .contactsList(list3)
                    .status(ContactStatus.STALLED)
                    .build();
            contactRepository.save(contact5);

            Contact contact6 = Contact.builder()
                    .id(6L)
                    .name("Jovana Pavlović")
                    .companyName("Former Partners Inc.")
                    .phoneNumber("+381686789012")
                    .email("jovana.pavlovic@formerpartners.com")
                    .contactsList(list3)
                    .status(ContactStatus.REJECTED)
                    .build();
            contactRepository.save(contact6);
        }
    }

    public void addMockProjectTaskStatusData(){
        TaskStatus projectTaskStatus = new TaskStatus().builder()
                .id(1L)
                .name("OPEN")
                .projectId(1L)
                .build();

        taskStatusRepository.save(projectTaskStatus);

        projectTaskStatus.setId(2L);
        projectTaskStatus.setName("IN PROGRESS");

        taskStatusRepository.save(projectTaskStatus);

        projectTaskStatus.setId(3L);
        projectTaskStatus.setName("CLOSED");

        taskStatusRepository.save(projectTaskStatus);

        projectTaskStatus.setId(4L);
        projectTaskStatus.setName("DONE");

        taskStatusRepository.save(projectTaskStatus);
    }

    public void addMockProjectData(){

        User user1 = userRepository.findById(1L).orElse(null);
        User user2 = userRepository.findById(2L).orElse(null);

        // 1. Kreiraj i sacuvaj projekat
        Project project = Project.builder()
                .name("Projekat Alfa")
                .description("Opis projekta Alfa")
                .companyId(1L)
                .teamId(1L)
                .note("Neki važan note za projekat")
                .startDate(Date.from(Instant.now()))
                .projectTasks(new ArrayList<>())
                .users(Set.of(user1, user2))
                .build();
        projectRepository.save(project);

        // 2. Ucitaj korisnike (pretpostavljam da postoje)
        //User user1 = userRepository.findById(1L).orElseThrow(() -> new RuntimeException("User 1 nije pronađen"));
        //User user2 = userRepository.findById(2L).orElseThrow(() -> new RuntimeException("User 2 nije pronađen"));



        // 3. Kreiraj task 1 i sacuvaj
        ProjectTask task1 = ProjectTask.builder()
                .name("Task 1")
                .description("Opis taska 1")
                .project(project)
                .user(user1)
                .dateDue(new Date(System.currentTimeMillis() + 3 * 24 * 60 * 60 * 1000)) // 3 dana od sad
                .priority(TaskPriority.HIGH)
                .statusId(1L)
                .users(Set.of(user1, user2))
                .notes(new ArrayList<>())
                .build();
        projectTaskRepository.save(task1);

        // 4. Kreiraj task 2 i sacuvaj
        ProjectTask task2 = ProjectTask.builder()
                .name("Task 2")
                .description("Opis taska 2")
                .project(project)
                .user(user1)
                .dateDue(new Date(System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000)) // 5 dana od sad
                .priority(TaskPriority.NORMAL)
                .statusId(3L)
                .users(Set.of(user1))
                .notes(new ArrayList<>())
                .build();
        projectTaskRepository.save(task2);

        // 5. Kreiraj note za task1 i sacuvaj ih
        TaskNote note1 = TaskNote.builder()
                .note("Napomena 1 za task 1")
                .userId(user1.getId())
                .projectTask(task1)
                .dateTime(new Date())
                .build();
        taskNoteRepository.save(note1);

        TaskNote note2 = TaskNote.builder()
                .note("Napomena 2 za task 1")
                .userId(user2.getId())
                .projectTask(task1)
                .dateTime(new Date())
                .build();
        taskNoteRepository.save(note2);

    }

    @Transactional
    public void addMockMovableAssetsData() {

        Company company = companyRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Company with ID 1 not found"));

        Location location = locationRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Location with ID 1 not found"));

        User issuedBy = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("IssuedBy user not found"));

        User currentUser = userRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Current user not found"));


        MovableAsset asset1 = MovableAsset.builder()
                .identifier("MA-0001")
                .name("Laptop Dell XPS 15")
                .barcode("BA-0001")
                .type("LAPTOP")
                .manufacturer("Dell")
                .model("XPS 15")
                .category("IT_EQUIPMENT")
                .serialNumber("SN-001")
                .movableAssetStatus(MovableAssetStatus.AVAILABLE)
                .company(company)
                .location(location)
                .issuedBy(issuedBy)
                .currentUser(currentUser)
                .purchaseDate(new Date())
                .unit("piece")
                .amount(1)
                .comment("Issued by admin, assigned to employee")
                .build();

        MovableAsset asset2 = MovableAsset.builder()
                .identifier("MA-0002")
                .name("iPhone 14 Pro")
                .barcode("BA-0002")
                .type("PHONE")
                .manufacturer("Apple")
                .model("14 Pro")
                .category("MOBILE_DEVICE")
                .serialNumber("SN-002")
                .movableAssetStatus(MovableAssetStatus.IN_USE)
                .company(company)
                .location(location)
                .issuedBy(issuedBy)
                .currentUser(currentUser)
                .purchaseDate(new Date())
                .unit("piece")
                .amount(1)
                .comment("Company phone")
                .build();

        movableAssetRepository.saveAll(List.of(asset1, asset2));

    }

*/
}
