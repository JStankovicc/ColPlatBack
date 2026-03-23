package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.enums.ProductType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "storage_location")
public class StorageLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shelf_id")
    private WarehouseShelf shelf;

    @Column(unique = true, nullable = false)
    private String barcode;

    private Double maxWeight;
    private Double currentWeight;

    private Double maxVolume;
    private Double currentVolume;

    @ManyToOne
    private Product preferredProduct;

    @Enumerated(EnumType.STRING)
    private ProductType preferredType;

    private boolean isOccupied;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}