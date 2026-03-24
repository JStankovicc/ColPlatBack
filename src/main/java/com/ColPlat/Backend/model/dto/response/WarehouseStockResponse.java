package com.ColPlat.Backend.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseStockResponse {

    private Long id;

    private WarehouseResponse warehouseResponse;

    private ProductResponse productResponse;

    private double amount;

}
