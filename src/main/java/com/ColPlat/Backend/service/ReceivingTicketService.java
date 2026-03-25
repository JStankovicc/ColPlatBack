package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketRequest;
import com.ColPlat.Backend.model.dto.response.ReceivingTicketResponse;
import com.ColPlat.Backend.model.entity.ReceivingTicket;

import java.util.List;
import java.util.Optional;

public interface ReceivingTicketService {
    ReceivingTicketResponse getById(Long id);
    List<ReceivingTicketResponse> findAllByWarehouseZone(Long warehouseZoneId);
    void save(ReceivingTicketRequest receivingTicketRequest);
    void changeStatus(Long receivingTicketId);
}