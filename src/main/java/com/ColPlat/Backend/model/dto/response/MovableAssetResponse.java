package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.Company;
import com.ColPlat.Backend.model.entity.Location;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.model.enums.MovableAssetStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovableAssetResponse {
    private Long id;
    private String identifier;
    private Company company;
    private String name;
    private String barcode;
    private String type;
    private String model;
    private String manufacturer;
    private String category;
    private String serialNumber;
    private Location location;
    private UserResponse currentUser;
    private UserResponse issuedBy;
    private MovableAssetStatus movableAssetStatus;
    private Date purchaseDate;
    private Date insuranceDate;
    private String comment;
    private String unit;
    private int amount;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
