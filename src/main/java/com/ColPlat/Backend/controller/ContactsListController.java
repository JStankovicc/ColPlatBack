package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.ContactsListsRequest;
import com.ColPlat.Backend.model.dto.response.ContactResponse;
import com.ColPlat.Backend.model.dto.response.ContactsListResponse;
import com.ColPlat.Backend.model.enums.ContactsListStatus;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contact")
@RequiredArgsConstructor
public class ContactsListController {

    private final CompanyService companyService;
    private final ContactListService contactListService;
    private final ContactService contactService;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/lists/all")
    public ResponseEntity<List<ContactsListResponse>> getAllContactsLists(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("type") String type) {

        String token = authorizationHeader.replace("Bearer ", "");

        ContactsListStatus request;
        try {
            request = ContactsListStatus.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        Long companyId = companyService.getCompanyFromToken(token).getId();

        List<ContactsListResponse> response = contactListService.getCompanyContacts(companyId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/sales/all")
    public ResponseEntity<List<ContactResponse>> getAllSalesContacts(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(contactService.findAllByUserId(userService.findByEmail(jwtService.extractUserName(token)).getId()));
    }

}
