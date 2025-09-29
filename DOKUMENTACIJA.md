# ColPlat Backend - Kompletna Dokumentacija

## Sadržaj
1. [Pregled projekta](#pregled-projekta)
2. [Konfiguracija](#konfiguracija)
3. [Entiteti](#entiteti)
4. [Enumi](#enumi)
5. [DTO objekti](#dto-objekti)
6. [API rute](#api-rute)
7. [WebSocket](#websocket)
8. [Sigurnost](#sigurnost)

---

## Pregled projekta

**ColPlat Backend** je Spring Boot aplikacija napisana u Javi 17 koja pruža backend funkcionalnosti za kolaborativnu platformu. Aplikacija koristi:

- **Spring Boot 3.3.4**
- **Spring Security** za autentifikaciju i autorizaciju
- **JWT** za token-based autentifikaciju
- **Spring Data JPA** za rad sa bazom podataka
- **MySQL** kao baza podataka
- **WebSocket** za real-time komunikaciju
- **Lombok** za smanjenje boilerplate koda

---

## Konfiguracija

### application.properties

```properties
# Osnovne postavke aplikacije
spring.application.name=ColPlatBackend

# Konfiguracija baze podataka
spring.datasource.url=jdbc:mysql://localhost:3306/colplat?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8
spring.datasource.username=root
spring.datasource.password=ROOT

# JPA/Hibernate konfiguracija
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.connection.characterEncoding=utf8
spring.jpa.properties.hibernate.connection.charSet=utf8
spring.jpa.open-in-view=false

# JWT konfiguracija
token.signing.key=u0oXbPomLW4qxqlz3/c+46U2so4o6d6UbONeAWS17oI=
```

### application-docker.properties

```properties
# Docker environment konfiguracija
spring.profiles.active=docker

# Database konfiguracija za Docker
spring.datasource.url=jdbc:mysql://mysql:3306/colplat_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=colplat_user
spring.datasource.password=colplat_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Server konfiguracija
server.port=8080

# CORS konfiguracija za Docker
app.cors.allowed-origins=http://localhost:80,http://127.0.0.1:80

# JWT konfiguracija
app.jwt.secret=${JWT_SECRET:mySecretKey}
app.jwt.expiration=${JWT_EXPIRATION:86400000}

# Actuator za health checks
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=when-authorized

# Logging
logging.level.com.ColPlat.Backend=INFO
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.security=DEBUG
```

---

## Entiteti

### User
```java
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private Long companyId;
    private Long userProfileId;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;
    private boolean active;
    private boolean cookiesEnabled;
    private boolean termsAndConditions;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### Company
```java
@Entity
@Table(name = "_company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String registryNum;
    private long locationId;
    @Lob
    @Column(name = "company_logo_pic", columnDefinition = "BLOB")
    private byte[] companyLogoPic;
    private Long billingDetailsId;
    private boolean termsAndConditionsAccepted;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<SupportType> supportTypes;
    private int basicProfilesNum;
    private int advancedProfilesNum;
    private int premiumProfilesNum;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### UserProfile
```java
@Entity
@Table(name = "_user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String displayName;
    private String firstName;
    private String lastName;
    @Lob
    @Column(name = "profile_pic", columnDefinition = "BLOB")
    private byte[] profilePic;
    private long userSettingId;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
```

### Project
```java
@Entity
@Table(name = "_project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Long companyId;
    private Long teamId;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ProjectTask> projectTasks;
    private String note;
}
```

### ProjectTask
```java
@Entity
@Table(name = "_project_task")
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(mappedBy = "projectTask", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TaskNote> notes;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private Date dateDue;
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;
    private String status;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> users;
}
```

### Contact
```java
@Entity
@Table(name = "_contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String companyName;
    private String phoneNumber;
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacts_list_id", nullable = false)
    private ContactsList contactsList;
    @Enumerated(EnumType.STRING)
    private ContactStatus status;
}
```

### Team
```java
@Entity
@Table(name = "_team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
    private Long companyId;
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> userIds = new HashSet<>();
}
```

### CalendarEvent
```java
@Entity
@Table(name = "_calendar_events")
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private LocalDateTime startDateTime;
    @Column(nullable = false)
    private LocalDateTime endDateTime;
    @Enumerated(EnumType.STRING)
    private EventPriority priority;
    @Column(nullable = false)
    private Long createdByUserId;
    private Long teamId;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventParticipant> participants;
}
```

### Conversation
```java
@Entity
@Table(name = "_conversation")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="company_id", nullable = false)
    private Long companyId;
    @Column(name="is_group", nullable = false)
    private boolean group;
    private String name;
    @CreationTimestamp
    @Column(name="created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name="last_message_at")
    private LocalDateTime lastMessageAt;
    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ConversationParticipant> participants = new HashSet<>();
}
```

### Message
```java
@Entity
@Table(name = "_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="conversation_id", nullable=false)
    private Conversation conversation;
    @Column(name="sender_id", nullable=false)
    private Long senderId;
    @Lob
    @Column(name="content", nullable=false)
    private String content;
    @CreationTimestamp
    @Column(name="created_at", nullable=false, updatable=false)
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false)
    private MessageStatus status = MessageStatus.SENT;
}
```

---

## Enumi

### Role
```java
public enum Role {
    ADMIN,
    SALES_MANAGEMENT,
    SALES,
    PROJECT_MANAGEMENT,
    PROJECT
}
```

### ContactStatus
```java
public enum ContactStatus {
    NEW,
    CONTACTED,
    OFFERED,
    CLOSED,
    REJECTED,
    STALLED
}
```

### SupportType
```java
public enum SupportType {
    EMAIL,
    CHAT,
    PHONE
}
```

### TaskPriority
```java
public enum TaskPriority {
    LOW,
    NORMAL,
    HIGH,
    URGENT
}
```

### EventPriority
```java
public enum EventPriority {
    LOW("Low"),
    NORMAL("Normal"),
    HIGH("High"),
    URGENT("Urgent");
    
    private final String displayName;
    
    EventPriority(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
```

### MessageStatus
```java
public enum MessageStatus {
    SENT, 
    DELIVERED, 
    READ
}
```

### ParticipationStatus
```java
public enum ParticipationStatus {
    INVITED("Invited"),
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    TENTATIVE("Maybe"),
    NO_RESPONSE("No Response");
    
    private final String displayName;
    
    ParticipationStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
```

### ContactsListStatus
```java
public enum ContactsListStatus {
    LEAD,
    CLIENT
}
```

### DepartmentType
```java
public enum DepartmentType {
    MANAGEMENT,
    FINANCE,
    SALES_MANAGEMENT,
    PROJECT_MANAGEMENT,
    SALES,
    PROJECT
}
```

---

## DTO objekti

### Request DTO-ovi

#### SignUpRequest
```java
public class SignUpRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
```

#### SignInRequest
```java
public class SignInRequest {
    private String email;
    private String password;
}
```

#### SignInWithTokenRequest
```java
public class SignInWithTokenRequest {
    private String token;
}
```

#### CalendarEventRequest
```java
public class CalendarEventRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String description;
    @NotNull(message = "Start date time is required")
    private LocalDateTime startDateTime;
    @NotNull(message = "End date time is required")
    private LocalDateTime endDateTime;
    private EventPriority priority;
    private Long teamId;
    private List<Long> participantUserIds;
}
```

#### SendMessageRequest
```java
public class SendMessageRequest {
    private Long conversationId;
    private String content;
}
```

#### CreateDirectConversationRequest
```java
public class CreateDirectConversationRequest {
    private Long otherUserId;
}
```

#### CreateGroupConversationRequest
```java
public class CreateGroupConversationRequest {
    private String name;
    private List<Long> participantIds;
}
```

#### ContactMessageRequest
```java
public class ContactMessageRequest {
    private String name;
    private String email;
    private String message;
}
```

#### UpdateContactStatusRequest
```java
public class UpdateContactStatusRequest {
    private String email;
}
```

#### UserProfileWithPasswordRequest
```java
public class UserProfileWithPasswordRequest {
    private String displayName;
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;
}
```

#### UserProfileWithoutPasswordRequest
```java
public class UserProfileWithoutPasswordRequest {
    private String displayName;
    private String firstName;
    private String lastName;
}
```

#### ProjectNoteUpdateRequest
```java
public class ProjectNoteUpdateRequest {
    private Long id;
    private String note;
}
```

#### ProjectTaskUpdateRequest
```java
public class ProjectTaskUpdateRequest {
    private Long id;
    private String name;
    private String description;
    private Date dateDue;
    private TaskPriority priority;
    private String status;
}
```

### Response DTO-ovi

#### JwtAuthenticationResponse
```java
public class JwtAuthenticationResponse {
    private String token;
    private List<String> role;
}
```

#### UserProfileResponse
```java
public class UserProfileResponse {
    private String displayName;
    private String name;
    private byte[] profilePic;
}
```

#### CompanyResponse
```java
public class CompanyResponse {
    private String companyName;
    private byte[] logoPic;
}
```

#### CompanySettingsInfoResponse
```java
public class CompanySettingsInfoResponse {
    private String companyName;
    private String registryNum;
    private byte[] logoPic;
    private Set<SupportType> supportTypes;
    private int basicProfilesNum;
    private int advancedProfilesNum;
    private int premiumProfilesNum;
}
```

#### CalendarEventResponse
```java
public class CalendarEventResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private EventPriority priority;
    private Long createdByUserId;
    private String createdByUserName;
    private Long teamId;
    private String teamName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ParticipantResponse> participants;
    
    @Data
    public static class ParticipantResponse {
        private Long userId;
        private String userName;
        private ParticipationStatus status;
    }
}
```

#### MessageResponse
```java
public class MessageResponse {
    private Long id;
    private Long conversationId;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
    private String status; // SENT/DELIVERED/READ
}
```

#### ConversationSummaryResponse
```java
public class ConversationSummaryResponse {
    private Long id;
    private String name;
    private boolean isGroup;
    private String lastMessageContent;
    private LocalDateTime lastMessageAt;
    private Long unreadCount;
    private List<UserResponse> participants;
}
```

#### ContactResponse
```java
public class ContactResponse {
    private String name;
    private String companyName;
    private String phoneNumber;
    private String email;
    private String status;
}
```

#### ContactsListResponse
```java
public class ContactsListResponse {
    private Long id;
    private String name;
    private ContactsListStatus status;
    private List<ContactResponse> contacts;
}
```

#### TeamResponse
```java
public class TeamResponse {
    private Integer id;
    private String name;
    private String description;
    private String departmentName;
    private List<UserResponse> members;
}
```

#### UserResponse
```java
public class UserResponse {
    private Long id;
    private String displayName;
    private String email;
    private byte[] profilePic;
    private Set<Role> roles;
}
```

---

## API rute

### Autentifikacija (`/api/v1/auth`)

#### POST `/api/v1/auth/signup`
Registracija novog korisnika.

**Request Body:**
```json
{
    "firstName": "string",
    "lastName": "string", 
    "email": "string",
    "password": "string"
}
```

**Response:**
```json
{
    "token": "string",
    "role": ["string"]
}
```

#### POST `/api/v1/auth/signin`
Prijavljivanje korisnika.

**Request Body:**
```json
{
    "email": "string",
    "password": "string"
}
```

**Response:**
```json
{
    "token": "string",
    "role": ["string"]
}
```

#### POST `/api/v1/auth/token`
Prijavljivanje sa tokenom.

**Request Body:**
```json
{
    "token": "string"
}
```

**Response:**
```json
{
    "token": "string",
    "role": ["string"]
}
```

### Korisnici (`/api/v1/user`)

#### GET `/api/v1/user/all`
Dohvatanje svih korisnika.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "id": 1,
        "email": "string",
        "companyId": 1,
        "userProfileId": 1,
        "roles": ["ADMIN"],
        "active": true,
        "cookiesEnabled": true,
        "termsAndConditions": true,
        "createdAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00"
    }
]
```

#### POST `/api/v1/user/add`
Dodavanje novog korisnika.

**Request Body:**
```json
{
    "email": "string",
    "password": "string",
    "companyId": 1,
    "roles": ["USER"]
}
```

#### DELETE `/api/v1/user/delete`
Brisanje korisnika po email-u.

**Query Parameters:**
- `email`: string

### Profil korisnika (`/api/v1/userProfile`)

#### GET `/api/v1/userProfile/getUserProfile`
Dohvatanje profila trenutnog korisnika.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
{
    "displayName": "string",
    "name": "string",
    "profilePic": "byte[]"
}
```

#### POST `/api/v1/userProfile/uploadProfilePic`
Upload profilne slike.

**Headers:** `Authorization: Bearer {token}`

**Request:** `multipart/form-data`
- `file`: MultipartFile

**Response:** `byte[]` (slika)

#### POST `/api/v1/userProfile/updateProfileWithoutPassword`
Ažuriranje profila bez promene lozinke.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "displayName": "string",
    "firstName": "string",
    "lastName": "string"
}
```

**Response:**
```json
{
    "token": "string",
    "role": ["string"]
}
```

#### POST `/api/v1/userProfile/updateProfileWithPassword`
Ažuriranje profila sa promenom lozinke.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "displayName": "string",
    "firstName": "string",
    "lastName": "string",
    "oldPassword": "string",
    "newPassword": "string"
}
```

**Response:**
```json
{
    "token": "string",
    "role": ["string"]
}
```

### Kompanija (`/api/v1/company`)

#### GET `/api/v1/company/getCompanyInfo`
Dohvatanje informacija o kompaniji.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
{
    "companyName": "string",
    "logoPic": "byte[]"
}
```

#### GET `/api/v1/company/getCompanySettingsInfo`
Dohvatanje postavki kompanije.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
{
    "companyName": "string",
    "registryNum": "string",
    "logoPic": "byte[]",
    "supportTypes": ["EMAIL", "CHAT", "PHONE"],
    "basicProfilesNum": 10,
    "advancedProfilesNum": 5,
    "premiumProfilesNum": 2
}
```

#### POST `/api/v1/company/uploadLogo`
Upload logoa kompanije.

**Headers:** `Authorization: Bearer {token}`

**Request:** `multipart/form-data`
- `file`: MultipartFile

**Response:** `byte[]` (slika)

#### GET `/api/v1/company/getAllSupportTypes`
Dohvatanje svih tipova podrške.

**Response:**
```json
["EMAIL", "CHAT", "PHONE"]
```

### Projekti (`/api/v1/project`)

#### GET `/api/v1/project/tasks/my`
Dohvatanje taskova trenutnog korisnika.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "id": 1,
        "name": "string",
        "description": "string",
        "dateDue": "2024-01-01T00:00:00",
        "priority": "HIGH",
        "status": "string",
        "project": {...},
        "user": {...},
        "users": [...]
    }
]
```

#### GET `/api/v1/project/taskStatus/getAll`
Dohvatanje svih statusa taskova.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "id": 1,
        "name": "string",
        "description": "string"
    }
]
```

#### GET `/api/v1/project/info`
Dohvatanje informacija o projektu.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
{
    "id": 1,
    "name": "string",
    "description": "string",
    "companyId": 1,
    "teamId": 1,
    "note": "string",
    "projectTasks": [...]
}
```

#### PUT `/api/v1/project/updateNote`
Ažuriranje beleške projekta.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "id": 1,
    "note": "string"
}
```

**Response:** `"Beleška uspešno ažurirana"`

#### PUT `/api/v1/project/tasks/update`
Ažuriranje taska.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "id": 1,
    "name": "string",
    "description": "string",
    "dateDue": "2024-01-01T00:00:00",
    "priority": "HIGH",
    "status": "string"
}
```

**Response:** `"Task uspešno ažuriran"`

### Timovi (`/api/v1/team`)

#### GET `/api/v1/team/getAllSalesTeams`
Dohvatanje svih prodajnih timova.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "id": 1,
        "name": "string",
        "description": "string",
        "departmentName": "string",
        "members": [...]
    }
]
```

### Kontakti (`/api/v1/contact`)

#### POST `/api/v1/contact/updateStatus`
Ažuriranje statusa kontakta.

**Headers:** `Authorization: Bearer {token}`

**Query Parameters:**
- `status`: string (NOVI, KONTAKTIRAN, PONUDA DATA, ZATVOREN, ODBIJEN, ZASTAO)

**Request Body:**
```json
{
    "email": "string"
}
```

**Response:** `"Status kontakta uspešno ažuriran"`

#### POST `/api/v1/contact/lists/all`
Dohvatanje svih liste kontakata.

**Headers:** `Authorization: Bearer {token}`

**Query Parameters:**
- `type`: string (LEAD, CLIENT)

**Response:**
```json
[
    {
        "id": 1,
        "name": "string",
        "status": "LEAD",
        "contacts": [...]
    }
]
```

#### GET `/api/v1/contact/user/sales/all`
Dohvatanje svih prodajnih kontakata korisnika.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "name": "string",
        "companyName": "string",
        "phoneNumber": "string",
        "email": "string",
        "status": "NEW"
    }
]
```

### Statusi kontakata (`/api/v1/contactStatus`)

#### GET `/api/v1/contactStatus/all`
Dohvatanje svih statusa kontakata.

**Response:**
```json
["Novi", "Kontaktiran", "Ponuda data", "Zatvoren", "Odbijen", "Zastao"]
```

### Poruke kontakata (`/api/v1/msg`)

#### POST `/api/v1/msg/new`
Slanje nove poruke kontaktu.

**Request Body:**
```json
{
    "name": "string",
    "email": "string",
    "message": "string"
}
```

**Response:** `"Success"`

### Kalendar (`/api/v1/calendar`)

#### POST `/api/v1/calendar/events/my`
Dohvatanje događaja korisnika.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "startDate": "2024-01-01T00:00:00",
    "endDate": "2024-01-31T23:59:59"
}
```

**Response:**
```json
[
    {
        "id": 1,
        "title": "string",
        "description": "string",
        "startDateTime": "2024-01-01T00:00:00",
        "endDateTime": "2024-01-01T01:00:00",
        "priority": "HIGH",
        "createdByUserId": 1,
        "createdByUserName": "string",
        "teamId": 1,
        "teamName": "string",
        "createdAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00",
        "participants": [...]
    }
]
```

#### GET `/api/v1/calendar/events/team/{teamId}`
Dohvatanje događaja tima.

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `teamId`: Long

**Query Parameters:**
- `startDate`: LocalDateTime
- `endDate`: LocalDateTime

**Response:** Lista događaja (isti format kao gore)

#### POST `/api/v1/calendar/events`
Kreiranje novog događaja.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "title": "string",
    "description": "string",
    "startDateTime": "2024-01-01T00:00:00",
    "endDateTime": "2024-01-01T01:00:00",
    "priority": "HIGH",
    "teamId": 1,
    "participantUserIds": [1, 2, 3]
}
```

**Response:** Događaj (isti format kao gore)

#### PUT `/api/v1/calendar/events/{eventId}`
Ažuriranje događaja.

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `eventId`: Long

**Request Body:** Isti kao za kreiranje

**Response:** Ažurirani događaj

#### DELETE `/api/v1/calendar/events/{eventId}`
Brisanje događaja.

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `eventId`: Long

**Response:** 204 No Content

#### POST `/api/v1/calendar/events/{eventId}/respond`
Odgovor na događaj.

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `eventId`: Long

**Query Parameters:**
- `response`: ParticipationStatus (INVITED, ACCEPTED, DECLINED, TENTATIVE, NO_RESPONSE)

**Response:** 200 OK

### Inbox (`/api/v1/inbox`)

#### GET `/api/v1/inbox/me`
Dohvatanje ID-a trenutnog korisnika.

**Headers:** `Authorization: Bearer {token}`

**Response:** `1`

#### GET `/api/v1/inbox/contacts/all`
Dohvatanje svih kontakata kompanije.

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "id": 1,
        "displayName": "string",
        "email": "string",
        "profilePic": "byte[]",
        "roles": ["USER"]
    }
]
```

#### GET `/api/v1/inbox/threads`
Dohvatanje inbox-a (konverzacija).

**Headers:** `Authorization: Bearer {token}`

**Response:**
```json
[
    {
        "id": 1,
        "name": "string",
        "isGroup": false,
        "lastMessageContent": "string",
        "lastMessageAt": "2024-01-01T00:00:00",
        "unreadCount": 5,
        "participants": [...]
    }
]
```

#### POST `/api/v1/inbox/threads/direct`
Kreiranje ili dohvatanje direktne konverzacije.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "otherUserId": 2
}
```

**Response:** `1` (ID konverzacije)

#### POST `/api/v1/inbox/threads/group`
Kreiranje grupne konverzacije.

**Headers:** `Authorization: Bearer {token}`

**Request Body:**
```json
{
    "name": "string",
    "participantIds": [1, 2, 3]
}
```

**Response:** `1` (ID konverzacije)

#### GET `/api/v1/inbox/threads/{id}/messages`
Dohvatanje poruka iz konverzacije (paginacija).

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `id`: Long

**Query Parameters:**
- `page`: int (default: 0)
- `size`: int (default: 50)

**Response:**
```json
{
    "content": [
        {
            "id": 1,
            "conversationId": 1,
            "senderId": 1,
            "content": "string",
            "createdAt": "2024-01-01T00:00:00",
            "status": "SENT"
        }
    ],
    "pageable": {...},
    "totalElements": 100,
    "totalPages": 2
}
```

#### POST `/api/v1/inbox/threads/{id}/messages`
Slanje poruke u konverzaciju.

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `id`: Long

**Request Body:**
```json
{
    "content": "string"
}
```

**Response:**
```json
{
    "id": 1,
    "conversationId": 1,
    "senderId": 1,
    "content": "string",
    "createdAt": "2024-01-01T00:00:00",
    "status": "SENT"
}
```

#### POST `/api/v1/inbox/threads/{id}/read`
Označavanje poruka kao pročitanih.

**Headers:** `Authorization: Bearer {token}`

**Path Parameters:**
- `id`: Long

**Query Parameters:**
- `upToMessageId`: Long

**Response:** 200 OK

### Lokacije (`/api/v1/location`)

#### GET `/api/v1/location/getAllCountries`
Dohvatanje svih zemalja.

**Response:**
```json
["Srbija", "Hrvatska", "Bosna i Hercegovina"]
```

#### GET `/api/v1/location/getRegionsByCountry`
Dohvatanje regiona po zemlji.

**Query Parameters:**
- `country`: string

**Response:**
```json
["Vojvodina", "Šumadija", "Južna Srbija"]
```

#### GET `/api/v1/location/getCitiesByRegion`
Dohvatanje gradova po regionu.

**Query Parameters:**
- `country`: string
- `region`: string

**Response:**
```json
["Beograd", "Novi Sad", "Niš"]
```

---

## WebSocket

### Chat WebSocket Controller

#### `/app/chat.send`
Slanje poruke preko WebSocket-a.

**Headers:**
- `user-id`: Long (ID korisnika)

**Request Body:**
```json
{
    "conversationId": 1,
    "content": "string"
}
```

**Response:** MessageResponse objekat

---

## Sigurnost

### JWT Autentifikacija

Aplikacija koristi JWT (JSON Web Token) za autentifikaciju. Token se šalje u `Authorization` header-u u formatu:

```
Authorization: Bearer {jwt_token}
```

### Sigurnosne konfiguracije

- **CORS** je konfigurisan za dozvoljene origin-e
- **JWT** tokeni imaju konfigurisanu ekspiraciju
- **Spring Security** je konfigurisan sa custom filterima
- **Password encoding** koristi BCrypt

### Rukovanje greškama

- **401 Unauthorized**: Nevaljan ili istekao token
- **403 Forbidden**: Korisnik nema dozvolu za pristup resursu
- **400 Bad Request**: Nevaljani podaci u request-u
- **500 Internal Server Error**: Greška na serveru

---

## Uputstvo za pisanje application.properties fajla

### 1. Osnovne postavke

```properties
# Naziv aplikacije
spring.application.name=ColPlatBackend

# Port na kom će aplikacija raditi
server.port=8080
```

### 2. Konfiguracija baze podataka

```properties
# MySQL konfiguracija
spring.datasource.url=jdbc:mysql://localhost:3306/colplat?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8
spring.datasource.username=root
spring.datasource.password=ROOT
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### 3. JPA/Hibernate konfiguracija

```properties
# Hibernate konfiguracija
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.connection.characterEncoding=utf8
spring.jpa.properties.hibernate.connection.charSet=utf8
spring.jpa.open-in-view=false
```

### 4. JWT konfiguracija

```properties
# JWT secret key (treba biti siguran i jedinstven)
token.signing.key=u0oXbPomLW4qxqlz3/c+46U2so4o6d6UbONeAWS17oI=
```

### 5. CORS konfiguracija

```properties
# Dozvoljeni origin-i za CORS
app.cors.allowed-origins=http://localhost:3000,http://localhost:80,http://127.0.0.1:80
```

### 6. Logging konfiguracija

```properties
# Nivoi logovanja
logging.level.com.ColPlat.Backend=INFO
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.security=DEBUG
```

### 7. Actuator konfiguracija

```properties
# Health check endpoint-i
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=when-authorized
```

### 8. Docker konfiguracija (application-docker.properties)

```properties
# Docker profil
spring.profiles.active=docker

# Docker MySQL konfiguracija
spring.datasource.url=jdbc:mysql://mysql:3306/colplat_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=colplat_user
spring.datasource.password=colplat_password

# Docker CORS
app.cors.allowed-origins=http://localhost:80,http://127.0.0.1:80

# Environment varijable za JWT
app.jwt.secret=${JWT_SECRET:mySecretKey}
app.jwt.expiration=${JWT_EXPIRATION:86400000}
```

### Napomene za konfiguraciju:

1. **Sigurnost**: Uvek koristite sigurne JWT secret key-jeve u produkciji
2. **Baza podataka**: Prilagodite connection string prema vašoj MySQL konfiguraciji
3. **CORS**: Dodajte samo potrebne origin-e za sigurnost
4. **Logging**: Smanjite nivo logovanja u produkciji
5. **Environment varijable**: Koristite environment varijable za osetljive podatke

---

*Dokumentacija je ažurirana: Januar 2024*
