package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.UpdateContactStatusRequest;
import com.ColPlat.Backend.model.entity.Contact;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.ContactStatus;
import com.ColPlat.Backend.service.ContactService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;
    private final JwtService jwtService;

    private static final Map<String, ContactStatus> STATUS_MAP = Map.of(
            "NOVI", ContactStatus.NEW,
            "KONTAKTIRAN", ContactStatus.CONTACTED,
            "PONUDA DATA", ContactStatus.OFFERED,
            "ZATVOREN", ContactStatus.CLOSED,
            "ODBijen", ContactStatus.REJECTED,
            "ZASTAO", ContactStatus.STALLED
    );


    @PostMapping("/updateStatus")
    public ResponseEntity<?> updateContactStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UpdateContactStatusRequest request,
            @RequestParam("status") String status) {

        try {
            String token = authorizationHeader.replace("Bearer ", "");

            ContactStatus contactStatus = STATUS_MAP.get(status.toUpperCase());
            if (contactStatus == null) {
                return ResponseEntity.badRequest()
                        .body("Nevaljan status: " + status + ". Dozvoljeni statusi: " + STATUS_MAP.keySet());
            }


            String userEmail = jwtService.extractUserName(token);
            User user = userService.findByEmail(userEmail);

            Contact contactOptional = contactService.findByEmail(request.getEmail());


            contactOptional.setStatus(contactStatus);
            contactService.save(contactOptional);

            return ResponseEntity.ok()
                    .body("Status kontakta " + request.getEmail() + " uspešno ažuriran na " + contactStatus);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Greška pri ažuriranju statusa kontakta: " + e.getMessage());
        }
    }
}

