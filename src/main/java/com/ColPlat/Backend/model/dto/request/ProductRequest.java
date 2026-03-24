package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Supplier;
import com.ColPlat.Backend.model.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private Long id;

    private String sku;

    private String name;

    private String description;

    private String barcode;

    private String unit;

    private String category;

    private int minStockLevel = 0;

    private int reorderPoint = 0;

    private Long supplierId;

    private ProductType productType;
}
