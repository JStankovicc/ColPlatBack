package com.ColPlat.Backend.model.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class WarehouseUserId implements Serializable {

    private Long warehouseId;
    private Long userId;

}