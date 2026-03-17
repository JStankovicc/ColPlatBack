package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.Warehouse;
import com.ColPlat.Backend.model.enums.WarehouseZoneType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseZoneResponse {
    private Long id;

    private Long warehouseId;

    private WarehouseZoneType type;

    private String code;
    private String name;
}
