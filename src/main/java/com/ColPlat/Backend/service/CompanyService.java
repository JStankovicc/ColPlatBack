package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.Location;

import java.util.List;

public interface CompanyService {

    Company getCompanyFromToken(String token);

    CompanyResponse getCompanyInfoFromToken(String token);

    CompanySettingsInfoResponse getCompanySettingsInfoFromToken(String token);
    
    Company findById(Long companyId);

    void replaceLogo(Company company, byte[] imageBytes);

    Company findCompanyFromUser(User user);

    CompanyResponse getCompanyResponseFromCompanyId(Long id);

    public void setNewHeadquarters(Company company, Location location);

}
