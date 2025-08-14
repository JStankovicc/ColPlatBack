package com.ColPlat.Backend.controller;

// package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.CreateDirectConversationRequest;
import com.ColPlat.Backend.model.dto.request.CreateGroupConversationRequest;
import com.ColPlat.Backend.model.dto.request.SendMessageRequest;
import com.ColPlat.Backend.model.dto.response.ConversationSummaryResponse;
import com.ColPlat.Backend.model.dto.response.MessageResponse;
import com.ColPlat.Backend.model.dto.response.UserResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/inbox")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class InboxController {

    private final CompanyService companyService;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final JwtService jwtService;
    private final ChatService chatService;

    // već imaš ovo – vraća sve korisnike firme:
    @GetMapping("/contacts/all")
    public ResponseEntity<List<UserResponse>> getAllContacts(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        Company company = companyService.getCompanyFromToken(token);
        List<User> users = userService.findAllByCompany(company.getId());

        ArrayList<UserResponse> userResponses = new ArrayList<>();
        for(User u : users){
            userResponses.add(userProfileService.getUserResponseFromUser(u));
        }
        return ResponseEntity.ok(userResponses);
    }

    // inbox: konverzacije gde učestvuje trenutni user
    @GetMapping("/threads")
    public ResponseEntity<List<ConversationSummaryResponse>> getInbox(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUserName(token);
        User me = userService.findByEmail(email);
        Company company = companyService.getCompanyFromToken(token);

        return ResponseEntity.ok(chatService.getInbox(company.getId(), me.getId()));
    }

    // kreiraj ili dohvati 1-1 konverzaciju
    @PostMapping("/threads/direct")
    public ResponseEntity<Long> createOrGetDirect(@RequestHeader("Authorization") String authorizationHeader,
                                                  @RequestBody CreateDirectConversationRequest req) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUserName(token);
        User me = userService.findByEmail(email);
        Company company = companyService.getCompanyFromToken(token);

        Long id = chatService.createOrGetDirect(company.getId(), me.getId(), req.getOtherUserId());
        return ResponseEntity.ok(id);
    }

    // kreiraj grupu
    @PostMapping("/threads/group")
    public ResponseEntity<Long> createGroup(@RequestHeader("Authorization") String authorizationHeader,
                                            @RequestBody CreateGroupConversationRequest req) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUserName(token);
        User me = userService.findByEmail(email);
        Company company = companyService.getCompanyFromToken(token);

        Long id = chatService.createGroup(company.getId(), me.getId(), req.getName(), req.getParticipantIds());
        return ResponseEntity.ok(id);
    }

    // poruke iz threada (paginacija)
    @GetMapping("/threads/{id}/messages")
    public ResponseEntity<Page<MessageResponse>> getMessages(@RequestHeader("Authorization") String authorizationHeader,
                                                             @PathVariable Long id,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "50") int size) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUserName(token);
        User me = userService.findByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(chatService.getMessages(id, me.getId(), pageable));
    }

    // pošalji poruku (REST varijanta, WS varijantu imaš gore)
    @PostMapping("/threads/{id}/messages")
    public ResponseEntity<MessageResponse> sendMessage(@RequestHeader("Authorization") String authorizationHeader,
                                                       @PathVariable Long id,
                                                       @RequestBody SendMessageRequest req) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUserName(token);
        User me = userService.findByEmail(email);
        return ResponseEntity.ok(chatService.sendMessage(id, me.getId(), req.getContent()));
    }

    // mark-as-read
    @PostMapping("/threads/{id}/read")
    public ResponseEntity<Void> markRead(@RequestHeader("Authorization") String authorizationHeader,
                                         @PathVariable Long id,
                                         @RequestParam Long upToMessageId) {
        String token = authorizationHeader.replace("Bearer ", "");
        String email = jwtService.extractUserName(token);
        User me = userService.findByEmail(email);
        chatService.markReadUpTo(id, me.getId(), upToMessageId);
        return ResponseEntity.ok().build();
    }
}
