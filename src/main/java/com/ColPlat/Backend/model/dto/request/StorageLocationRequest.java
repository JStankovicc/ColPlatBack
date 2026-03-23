package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.entity.Product;
import com.ColPlat.Backend.model.entity.WarehouseShelf;
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
public class StorageLocationRequest {

    private Long id;

    private String name;

    private Long shelfId;

    private String barcode;
    private boolean generateBarcode;

    private Double maxWeight;
    private Double currentWeight;

    private Double maxVolume;
    private Double currentVolume;

    private Long preferredProductId;

    private ProductType preferredType;

    private boolean isOccupied;

}
