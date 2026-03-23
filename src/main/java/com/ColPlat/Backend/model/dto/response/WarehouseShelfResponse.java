package com.ColPlat.Backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseShelfResponse {

    private Long id;
    private String name;
    private Long warehouseAisleId;
    private Integer level;

}
