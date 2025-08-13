package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.enums.ContactStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/contactStatus")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ContactStatusController {

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllContactStatuses() {
        Map<ContactStatus, String> statusSrpski = Map.of(
                ContactStatus.NEW, "Novi",
                ContactStatus.CONTACTED, "Kontaktiran",
                ContactStatus.OFFERED, "Ponuda data",
                ContactStatus.CLOSED, "Zatvoren",
                ContactStatus.REJECTED, "Odbijen",
                ContactStatus.STALLED, "Zastao"
        );

        List<String> statuses = Arrays.stream(ContactStatus.values())
                .map(statusSrpski::get)
                .toList();

        return ResponseEntity.ok(statuses);
    }



}
