package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.entity.WarehouseTask;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseTaskResponse {
    private Long id;
    private Long assignedUserId;
    private Long sourceLocationId;
    private String sourceLocationName;
    private Long destinationLocationId;
    private String destinationLocationName;
    private List<WarehouseTask.ProductAmount> items;
    private boolean completed;
    private LocalDateTime createdAt;
}