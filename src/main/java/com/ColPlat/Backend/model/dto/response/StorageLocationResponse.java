package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageLocationResponse {

    private Long id;

    private String name;

    private Long shelfId;

    private String barcode;

    private Double maxWeight;
    private Double currentWeight;

    private Double maxVolume;
    private Double currentVolume;

    private Product preferredProductId;

    private ProductType preferredType;

    private boolean isOccupied;

}
