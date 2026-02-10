package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.enums.MovableAssetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMovableAssetRequest {

    private Long id;
    private String identifier;
    private String name;
    private String barcode;
    private String type;
    private String model;
    private String manufacturer;
    private String category;
    private String serialNumber;
    //private Location location;
    private Long currentUserId;
    private MovableAssetStatus movableAssetStatus;
    private Date purchaseDate;
    private Date insuranceDate;
    private String comment;
    private String unit;
    private int amount;


}
