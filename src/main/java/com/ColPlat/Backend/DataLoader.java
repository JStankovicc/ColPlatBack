package com.ColPlat.Backend;

import com.ColPlat.Backend.model.entity.*;
import com.ColPlat.Backend.model.enums.*;
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

    // Repository-ji za kontakte
    private final ContactRepository contactRepository;
    private final ContactListRepository contactListRepository;

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

    @PostConstruct
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
                .cityId(1)    // Grad Beograd
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
                .regionId(2)  // Raski okrug
                .cityId(2)    // Kraljevo
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
                .cityId(1)    // Grad Beograd
                .contacts(new ArrayList<>())
                .status(ContactsListStatus.LEAD)
                .build();
        contactListRepository.save(list3);
    }

    @PostConstruct
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
}
