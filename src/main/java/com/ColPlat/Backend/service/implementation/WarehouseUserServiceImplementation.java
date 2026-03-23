package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.AddWarehouseUserRequest;
import com.ColPlat.Backend.model.dto.response.WarehouseResponse;
import com.ColPlat.Backend.model.dto.response.WarehouseUserResponse;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.model.entity.WarehouseUser;
import com.ColPlat.Backend.model.entity.WarehouseUserId;
import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.model.enums.WarehouseRole;
import com.ColPlat.Backend.repository.WarehouseUserRepository;
import com.ColPlat.Backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseUserServiceImplementation implements WarehouseUserService {

    private final WarehouseUserRepository warehouseUserRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final UserProfileService userProfileService;
    private final LocationService locationService;
    private final WarehouseService warehouseService;


    @Override
    public WarehouseRole getMyRole(String token, Long warehouseId) {
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (user.getRoles().contains(Role.ADMIN)) {
            return WarehouseRole.ADMIN;
        }

        WarehouseUserId id = WarehouseUserId.builder()
                .warehouseId(warehouseId)
                .userId(user.getId())
                .build();

        return warehouseUserRepository.findById(id)
                .map(WarehouseUser::getRole)
                .orElseThrow(() -> new IllegalStateException("User is not assigned to this warehouse"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseResponse> getMyWarehouses(String token) {
        String email = jwtService.extractUserName(token);
        User user = userService.findByEmail(email);

        return warehouseUserRepository.findByIdUserId(user.getId())
                .stream()
                .map(WarehouseUser::getWarehouse)
                .map(warehouse -> WarehouseResponse.builder()
                        .id(warehouse.getId())
                        .name(warehouse.getName())
                        .code(warehouse.getCode())
                        .manager(userProfileService.getUserResponseFromUser(warehouse.getManager()))
                        .openAt(warehouse.getOpenAt())
                        .closedAt(warehouse.getClosedAt())
                        .locationId(warehouse.getLocation().getId())
                        .location(locationService.getLocationStringFromId(warehouse.getLocation().getId()))
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public void saveOrUpdateUserWarehouse(AddWarehouseUserRequest request) {
        WarehouseUserId id = WarehouseUserId.builder()
                .userId(request.getUserId())
                .warehouseId(request.getWarehouseId())
                .build();

        User user = userService.findById(request.getUserId());

        Warehouse warehouse = warehouseService.findById(request.getWarehouseId());

        WarehouseUser warehouseUser = WarehouseUser.builder()
                .id(id)
                .user(user)
                .warehouse(warehouse)
                .role(request.getRole())
                .build();

        warehouseUserRepository.save(warehouseUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WarehouseUserResponse> getAllWarehouseUsers(Long warehouseId) {
        if (!warehouseService.existsById(warehouseId)) {
            throw new IllegalArgumentException("Magacin sa ID-em " + warehouseId + " ne postoji.");
        }
        List<WarehouseUser> warehouseUsers = warehouseUserRepository.findByIdWarehouseId(warehouseId);

        return warehouseUsers.stream()
                .map(wu -> WarehouseUserResponse.builder()
                        .warehouseId(wu.getId().getWarehouseId())
                        .role(wu.getRole())
                        .userProfileResponse(userProfileService.getUserProfileResponseFromUser(wu.getUser()))
                        .build())
                .toList();
    }
}
