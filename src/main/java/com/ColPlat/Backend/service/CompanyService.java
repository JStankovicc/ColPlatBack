package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;
import com.ColPlat.Backend.model.dto.response.CompanySettingsInfoResponse;

public interface CompanyService {
    CompanyResponse getCompanyInfoFromToken(String token);

    CompanySettingsInfoResponse getCompanySettingsInfoFromToken(String token);
}
