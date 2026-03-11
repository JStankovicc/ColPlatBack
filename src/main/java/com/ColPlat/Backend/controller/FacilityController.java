package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.request.CreateOfficeRequest;
import com.ColPlat.Backend.model.dto.response.OfficeResponse;
import com.ColPlat.Backend.model.entity.Office;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facility")
@RequiredArgsConstructor
public class FacilityController {

    private final OfficeService officeService;
    private final CompanyService companyService;


    @GetMapping("/office/all")
    public ResponseEntity<List<OfficeResponse>> getOfficesForCompany(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println(token);
        return ResponseEntity.ok(officeService.getAllOfficesByCompany(companyService.getCompanyFromToken(token)));
    }

    @PostMapping("/office")
    public void addOffice(@RequestHeader("Authorization") String authorizationHeader, @RequestBody CreateOfficeRequest request) {
        String token = authorizationHeader.replace("Bearer ", "");
        officeService.createOffice(request, companyService.getCompanyFromToken(token));
    }
}
