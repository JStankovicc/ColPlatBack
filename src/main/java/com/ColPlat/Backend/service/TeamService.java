package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.TeamResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Team;

import java.util.List;

public interface TeamService {

    Team getTeamById( Long id);

    List<TeamResponse> getAllSalesTeamsByCompany(Company company);
}
