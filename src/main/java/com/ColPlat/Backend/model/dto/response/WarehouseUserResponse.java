package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.enums.WarehouseRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseUserResponse {

    private Long warehouseId;
    private UserProfileResponse userProfileResponse;
    private WarehouseRole role;
}
