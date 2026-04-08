package com.ColPlat.Backend.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceivingTicketRequest {

    private Long id;

    private Long warehouseZoneId;

    private Long supplierId;

    private String referenceNumber;

    private String note;

    private List<ProductAmountDTO> items;

    @Data
    @Builder
    public static class ProductAmountDTO {
        private Long productId;
        private double amount;
    }

}
