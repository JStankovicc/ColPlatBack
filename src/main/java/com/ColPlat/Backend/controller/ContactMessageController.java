package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.ContactMessageRequest;
import com.ColPlat.Backend.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/msg")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/new")
    public ResponseEntity<String> postContactMessage(@RequestBody ContactMessageRequest contactMessageRequest){
        System.out.println("RADI");
        contactMessageService.addMessage(contactMessageRequest);
        return ResponseEntity.ok("Success");
    }

}
