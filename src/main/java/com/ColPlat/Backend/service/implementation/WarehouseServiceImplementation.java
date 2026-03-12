package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.CreateWarehouseRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.repository.WarehouseRepository;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImplementation implements WarehouseService {

    private final WarehouseRepository warehouseRepository;
    private final LocationService locationService;
    private final CompanyService companyService;
    private final UserService userService;
    private final UserProfileService userProfileService;

    @Override
    public void createWarehouse(CreateWarehouseRequest request, Company company) {
        Location location = Location.builder()
                .countryId(request.getCountryId())
                .regionId(request.getRegionId())
                .districtId(request.getDistrictId())
                .city(request.getCity())
                .address(request.getAddress())
                .build();

        location = locationService.createLocation(location);

        Warehouse warehouse = Warehouse.builder()
                .name(request.getName())
                .code(request.getCode())
                .openAt(request.getOpenAt())
                .closedAt(request.getClosedAt())
                .company(company)
                .location(location)
                .manager(userService.findById(request.getManagerId()))
                .build();

        warehouseRepository.save(warehouse);
    }

    @Override
    @Transactional
    public List<WarehouseResponse> getAllWarehousesByCompany(Company company) {
        List<Warehouse> warehouses = warehouseRepository.findAllByCompany(company);
        List<WarehouseResponse> warehouseResponses = new ArrayList<>();
        for (Warehouse warehouse : warehouses) {
            warehouseResponses.add(WarehouseResponse.builder()
                    .id(warehouse.getId())
                    .name(warehouse.getName())
                    .code(warehouse.getCode())
                    .manager(userProfileService.getUserResponseFromUser(warehouse.getManager()))
                    .openAt(warehouse.getOpenAt())
                    .closedAt(warehouse.getClosedAt())
                    .locationId(warehouse.getLocation().getId())
                    .location(locationService.getLocationStringFromId(warehouse.getLocation().getId()))
                    .build());
        }
        return warehouseResponses;
    }

    @Override
    @Transactional
    public void updateWarehouse(Long warehouseId, CreateWarehouseRequest request) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findById(warehouseId);
        if(warehouseOptional.isPresent()) {
            Warehouse warehouse = warehouseOptional.get();
            warehouse.setName(request.getName());
            warehouse.setCode(request.getCode());
            warehouse.setOpenAt(request.getOpenAt());
            warehouse.setClosedAt(request.getClosedAt());
            warehouse.setManager(userService.findById(request.getManagerId()));

            Location location = warehouse.getLocation();

            location.setCountryId(request.getCountryId());
            location.setRegionId(request.getRegionId());
            location.setDistrictId(request.getDistrictId());
            location.setCity(request.getCity());
            location.setAddress(request.getAddress());

            location = locationService.updateLocation(location);

            warehouse.setLocation(location);

            warehouseRepository.save(warehouse);
        }

    }

    @Override
    public void deleteWarehouse(Long warehouseId) {
        warehouseRepository.deleteById(warehouseId);
    }
}
