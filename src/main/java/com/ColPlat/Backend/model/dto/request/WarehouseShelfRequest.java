package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.entity.WarehouseAisle;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseShelfRequest {

    private Long id;
    private Long warehouseAisleId;
    private Integer level;
    private String name;

}
