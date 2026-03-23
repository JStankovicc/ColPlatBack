package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.WarehouseZone;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseAisleResponse {

    private Long id;
    private String name;
    private Long warehouseZoneId;
    private String code;

}
