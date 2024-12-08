package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;
import com.ColPlat.Backend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @RequestMapping("/getCompanyInfo")
    public ResponseEntity<CompanyResponse> getCompanyInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(companyService.getCompanyInfoFromToken(token));
    }

    @RequestMapping("/getCompanySettingsInfo")
    public ResponseEntity<CompanySettingsInfoResponse> getCompanySettingsInfo(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ","");
        return ResponseEntity.ok(companyService.getCompanySettingsInfoFromToken(token));
    }

}
