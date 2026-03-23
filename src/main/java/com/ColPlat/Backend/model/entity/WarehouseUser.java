package com.ColPlat.Backend.model.entity;

import com.ColPlat.Backend.model.enums.Role;
import com.ColPlat.Backend.model.enums.WarehouseRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "warehouse_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_warehouse_user", columnNames = {"warehouse_id", "user_id"})
        },
        indexes = {
                @Index(name = "idx_wu_warehouse", columnList = "warehouse_id"),
                @Index(name = "idx_wu_user", columnList = "user_id")
        }
)
public class WarehouseUser {

    @EmbeddedId
    private WarehouseUserId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("warehouseId")
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private WarehouseRole role;
}