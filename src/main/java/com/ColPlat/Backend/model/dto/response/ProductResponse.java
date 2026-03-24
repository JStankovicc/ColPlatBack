package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;

    private String name;

    private Long companyId;

    private String sku;

    private String description;

    private String barcode;

    private String unit;

    private String category;

    private int minStockLevel;

    private int reorderPoint;

    private SupplierResponse supplierResponse;

    private ProductType productType;

}
