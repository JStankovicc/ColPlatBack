package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.enums.MovableAssetStatus;
import jakarta.persistence.*;
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
@Entity
@Table(name = "_movable_asset")
public class MovableAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identifier;
    @ManyToOne
    @JoinColumn(name = "movable_asset_company_id")
    private Company company;
    private String name;
    private String barcode;
    private String type;
    private String model;
    private String manufacturer;
    private String category;
    private String serialNumber;
    @ManyToOne
    @JoinColumn(name = "movable_asset_location_id")
    private Location location;
    @ManyToOne
    @JoinColumn(name = "current_movable_asset_user_id")
    private User currentUser;
    @ManyToOne
    @JoinColumn(name = "current_movable_asset_issued_by_id")
    private User issuedBy;
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
