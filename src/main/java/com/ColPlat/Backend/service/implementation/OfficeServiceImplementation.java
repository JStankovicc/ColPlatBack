package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.CreateOfficeRequest;
import com.ColPlat.Backend.model.dto.response.OfficeResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.model.entity.Office;
import com.ColPlat.Backend.repository.OfficeRepository;
import com.ColPlat.Backend.service.CompanyService;
import com.ColPlat.Backend.service.LocationService;
import com.ColPlat.Backend.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OfficeServiceImplementation implements OfficeService {

    private final OfficeRepository officeRepository;
    private final LocationService locationService;
    private final CompanyService companyService;

    @Override
    public void createOffice(CreateOfficeRequest request, Company company) {
        Location location = Location.builder()
                .countryId(request.getCountryId())
                .regionId(request.getRegionId())
                .districtId(request.getDistrictId())
                .city(request.getCity())
                .address(request.getAddress())
                .build();

        location = locationService.createLocation(location);

        Office office = Office.builder()
                .name(request.getName())
                .code(request.getCode())
                .openAt(request.getOpenAt())
                .closedAt(request.getClosedAt())
                .location(location)
                .company(company)
                .maxDeskCapacity(request.getMaxDeskCapacity())
                .build();

        officeRepository.save(office);

        if(request.isNewHeadquarters()){
            companyService.setNewHeadquarters(company, location);
        }
    }

    @Override
    public List<OfficeResponse> getAllOfficesByCompany(Company company) {

        List<Office> offices = officeRepository.findAllByCompany(company);
        List<OfficeResponse> officesResponse = new ArrayList<>();
        for (Office office : offices) {
            OfficeResponse officeResponse = new OfficeResponse();
            officeResponse.setId(office.getId());
            officeResponse.setName(office.getName());
            officeResponse.setCode(office.getCode());
            officeResponse.setOpenAt(office.getOpenAt());
            officeResponse.setClosedAt(office.getClosedAt());
            officeResponse.setLocationId(office.getLocation().getId());
            officeResponse.setLocation(locationService.getLocationStringFromId(office.getLocation().getId()));
            officeResponse.setMaxDeskCapacity(office.getMaxDeskCapacity());
            officesResponse.add(officeResponse);
        }
        return officesResponse;
    }

    @Override
    @Transactional
    public void updateOffice(Long officeId, CreateOfficeRequest request) {
        Optional<Office> officeOptional = officeRepository.findById(officeId);

        if (officeOptional.isPresent()) {
            Office officeToUpdate = officeOptional.get();

            officeToUpdate.setName(request.getName());
            officeToUpdate.setCode(request.getCode());
            officeToUpdate.setOpenAt(request.getOpenAt());
            officeToUpdate.setClosedAt(request.getClosedAt());
            officeToUpdate.setMaxDeskCapacity(request.getMaxDeskCapacity());

            Location location = officeToUpdate.getLocation();

            location.setCountryId(request.getCountryId());
            location.setRegionId(request.getRegionId());
            location.setDistrictId(request.getDistrictId());
            location.setCity(request.getCity());
            location.setAddress(request.getAddress());

            location = locationService.updateLocation(location);

            officeToUpdate.setLocation(location);

            if (request.isNewHeadquarters()) {
                companyService.setNewHeadquarters(officeToUpdate.getCompany(), location);
            }

            officeRepository.save(officeToUpdate);
        }
    }

    @Override
    public void deleteOffice(Long officeId) {
        officeRepository.deleteById(officeId);
    }
}
