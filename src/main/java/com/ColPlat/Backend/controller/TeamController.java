package com.ColPlat.Backend.controller;

import com.ColPlat.Backend.model.dto.response.TeamResponse;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/team")
@RequiredArgsConstructor
public class TeamController {

    private final CompanyService companyService;
    private final TeamService teamService;

    @GetMapping("/getAllSalesTeams")
    public ResponseEntity<List<TeamResponse>> getAllSalesTeams(@RequestHeader("Authorization") String authorizationHeader){

        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(teamService.getAllSalesTeamsByCompany(companyService.getCompanyFromToken(token)));
    }


}
