package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketRequest;
import com.ColPlat.Backend.model.dto.response.ReceivingTicketResponse;
import com.ColPlat.Backend.model.entity.ProductAmount;
import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.WarehouseZone;
import com.ColPlat.Backend.repository.ReceivingTicketRepository;
import com.ColPlat.Backend.service.ProductService;
import com.ColPlat.Backend.service.ReceivingTicketService;
import com.ColPlat.Backend.service.SupplierService;
import com.ColPlat.Backend.service.WarehouseZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceivingTicketServiceImplementation implements ReceivingTicketService {

    private final ReceivingTicketRepository receivingTicketRepository;
    private final WarehouseZoneService warehouseZoneService;
    private final ProductService productService;
    private final SupplierService supplierService;


    @Override
    @Transactional(readOnly = true)
    public ReceivingTicketResponse getById(Long id) {
        Optional<ReceivingTicket> receivingTicketOptional = receivingTicketRepository.findById(id);
        return receivingTicketOptional.map(this::getReceivingTicketResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReceivingTicketResponse> findAllByWarehouseZone(Long warehouseZoneId) {
        WarehouseZone warehouseZone = warehouseZoneService.findById(warehouseZoneId);
        if(warehouseZone == null) return null;
        List<ReceivingTicket> receivingTickets = receivingTicketRepository.findByWarehouseZone(warehouseZone);
        List<ReceivingTicketResponse> receivingTicketResponses = new ArrayList<>();
        for(ReceivingTicket receivingTicket : receivingTickets){
            receivingTicketResponses.add(getReceivingTicketResponse(receivingTicket));
        }
        return receivingTicketResponses;
    }

    @Override
    @Transactional
    public void save(ReceivingTicketRequest receivingTicketRequest) {
        ReceivingTicket receivingTicket;
        List<ProductAmount> productAmounts;

        if(receivingTicketRequest.getId() != null){
            Optional<ReceivingTicket> receivingTicketOptional = receivingTicketRepository.findById(receivingTicketRequest.getId());
            if(receivingTicketOptional.isEmpty())return;
            receivingTicket = receivingTicketOptional.get();
            productAmounts = receivingTicket.getProductAmount();
        }else {
            receivingTicket = new ReceivingTicket();
            productAmounts = new ArrayList<>();
            receivingTicket.setWarehouseZone(warehouseZoneService.findById(receivingTicketRequest.getWarehouseZoneId()));
        }

        for(ReceivingTicketRequest.ProductAmountDTO productAmountDTO : receivingTicketRequest.getItems()){
            productAmounts.add(ProductAmount.builder()
                    .product(productService.findById(productAmountDTO.getProductId()))
                    .amount(productAmountDTO.getAmount())
                    .build()
            );
        }

        receivingTicket.setSupplier(supplierService.findById(receivingTicketRequest.getSupplierId()));
        receivingTicket.setReferenceNumber(receivingTicketRequest.getReferenceNumber());
        receivingTicket.setNote(receivingTicketRequest.getNote());
        receivingTicket.setProductAmount(productAmounts);

        receivingTicketRepository.save(receivingTicket);
    }

    @Override
    @Transactional
    public void changeStatus(Long receivingTicketId) {
        ReceivingTicket receivingTicket = receivingTicketRepository.findById(receivingTicketId).orElse(null);
        if(receivingTicket != null){
            receivingTicket.setActive(false);
            receivingTicketRepository.save(receivingTicket);
        }
    }

    @Transactional(readOnly = true)
    public ReceivingTicketResponse getReceivingTicketResponse(ReceivingTicket receivingTicket){
        List<ProductAmount> productAmountList = receivingTicket.getProductAmount();
        List<ReceivingTicketRequest.ProductAmountDTO> productAmountDTOS = new ArrayList<>();
        for(ProductAmount productAmount : productAmountList){
            productAmountDTOS.add(getProductAmountDTO(productAmount));
        }

        return ReceivingTicketResponse.builder()
                .id(receivingTicket.getId())
                .warehouseZoneId(receivingTicket.getWarehouseZone().getId())
                .supplierId(receivingTicket.getSupplier().getId())
                .referenceNumber(receivingTicket.getReferenceNumber())
                .note(receivingTicket.getNote())
                .items(productAmountDTOS)
                .updatedAt(receivingTicket.getUpdatedAt())
                .build();
    }

    public ReceivingTicketRequest.ProductAmountDTO getProductAmountDTO(ProductAmount productAmount){
        return ReceivingTicketRequest.ProductAmountDTO.builder()
                .productId(productAmount.getProduct().getId())
                .amount(productAmount.getAmount())
                .build();
    }
}
