package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.Warehouse;
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
public class WarehouseStockRequest {

    private Long id;

    private Long warehouseId;

    private Long productId;

    private double amount;


}
