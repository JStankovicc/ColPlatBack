package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.enums.WarehouseRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddWarehouseUserRequest {

    Long userId;
    Long warehouseId;
    WarehouseRole role;
}
