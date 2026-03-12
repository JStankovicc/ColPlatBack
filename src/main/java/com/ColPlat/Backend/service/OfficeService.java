package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.CreateOfficeRequest;
import com.ColPlat.Backend.model.dto.response.OfficeResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Office;

import java.util.List;

public interface OfficeService {

    public void createOffice(CreateOfficeRequest request, Company company);

    public List<OfficeResponse> getAllOfficesByCompany(Company company);

    public void updateOffice(Long officeId, CreateOfficeRequest request);

    public void deleteOffice(Long officeId);
}
