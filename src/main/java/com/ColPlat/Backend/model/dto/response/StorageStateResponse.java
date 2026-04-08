package com.ColPlat.Backend.model.dto.response;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StorageStateResponse {
    private Long storageLocationId;
    private Long productId;
    private Double amount;
}