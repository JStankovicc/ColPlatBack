package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.entity.WarehouseZone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseAisleRequest {

    private Long id;

    private String name;

    private Long warehouseZoneId;

    private String code;
}
