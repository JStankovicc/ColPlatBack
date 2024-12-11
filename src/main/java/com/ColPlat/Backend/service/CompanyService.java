package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;
import com.ColPlat.Backend.model.entity.Company;

public interface CompanyService {
    CompanyResponse getCompanyInfoFromToken(String token);

    CompanySettingsInfoResponse getCompanySettingsInfoFromToken(String token);
    
    Company findById(Long companyId);

    void replaceLogo(Company company, byte[] imageBytes);
}
