package com.ColPlat.Backend.model.dto.response;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketRequest;
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
public class ReceivingTicketResponse {

    private Long id;

    private Long warehouseZoneId;

    private Long supplierId;

    private String referenceNumber;

    private String note;

    private List<ReceivingTicketRequest.ProductAmountDTO> items;

    private LocalDateTime updatedAt;
}
