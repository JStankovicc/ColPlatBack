package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.SupportType;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/getCompanyInfo")
    public ResponseEntity<CompanyResponse> getCompanyInfo(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(companyService.getCompanyInfoFromToken(token));
    }

    @GetMapping("/getCompanySettingsInfo")
    public ResponseEntity<CompanySettingsInfoResponse> getCompanySettingsInfo(@RequestHeader("Authorization") String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ","");
        return ResponseEntity.ok(companyService.getCompanySettingsInfoFromToken(token));
    }

    @PostMapping("/uploadLogo")
    public ResponseEntity<byte[]> setLogoPic(@RequestHeader("Authorization") String authorizationHeader, @RequestParam("file") MultipartFile file){
        String token = authorizationHeader.replace("Bearer ", "");
        try {
            byte[] imageBytes = file.getBytes();
            String username = jwtService.extractUserName(token);
            User user = userService.findByEmail(username);
            Company company = companyService.findById(user.getCompanyId());
            companyService.replaceLogo(company, imageBytes);
            return ResponseEntity.ok(imageBytes);

        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/getAllSupportTypes")
    public ResponseEntity<List<String>> getAllSupportTypes(){
        SupportType[] supportTypes = SupportType.values();
        List<String> types = new ArrayList<>();
        for(SupportType s : supportTypes){
            types.add(s.toString());
        }
        return ResponseEntity.ok(types);
    }
}
