package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.response.CompanyResponse;

public interface CompanyService {
    CompanyResponse getCompanyInfoFromToken(String token);
}
