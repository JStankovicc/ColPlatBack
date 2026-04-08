package com.ColPlat.Backend.service.implementation;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketApprovalRequest;
import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.ReceivingTicketApproval;
import com.ColPlat.Backend.model.entity.User;
import com.ColPlat.Backend.repository.ReceivingTicketApprovalRepository;
import com.ColPlat.Backend.service.InventoryItemService;
import com.ColPlat.Backend.service.JwtService;
import com.ColPlat.Backend.service.ReceivingTicketApprovalService;
import com.ColPlat.Backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReceivingTicketApprovalServiceImplementation implements ReceivingTicketApprovalService {

    private final ReceivingTicketApprovalRepository receivingTicketApprovalRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final InventoryItemService inventoryItemService;

    @Override
    public void create(ReceivingTicketApproval receivingTicketApproval) {
        ReceivingTicketApproval approval;

        if(receivingTicketApproval.getId() != null){
            Optional<ReceivingTicketApproval> receivingTicketApprovalOptional = receivingTicketApprovalRepository.findById(receivingTicketApproval.getId());
            if(receivingTicketApprovalOptional.isEmpty()) return;
            approval = receivingTicketApprovalOptional.get();
        }else approval = new ReceivingTicketApproval();

        approval.setReceivingTicket(receivingTicketApproval.getReceivingTicket());
        approval.setApprovedBy(receivingTicketApproval.getApprovedBy());

        receivingTicketApprovalRepository.save(approval);

        //CREATETASKS
    }

    @Transactional
    @Override
    public void approve(String jwt, ReceivingTicketApprovalRequest receivingTicketApprovalRequest, ReceivingTicket receivingTicket){

        User user = userService.findByEmail(jwtService.extractUserName(jwt));
        User executedBy = userService.findById(receivingTicketApprovalRequest.getExecutedById());
        ReceivingTicketApproval receivingTicketApproval = ReceivingTicketApproval.builder()
                .approvedBy(user)
                .executedBy(executedBy)
                .comment(receivingTicketApprovalRequest.getComment())
                .receivingTicket(receivingTicket)
                .build();
        receivingTicketApprovalRepository.save(receivingTicketApproval);

        receivingTicket.getProductAmount().forEach(item -> {
            inventoryItemService.addInventory(
                    receivingTicketApprovalRequest.getNewStorageLocationId(),
                    item.getProduct().getId(),
                    item.getAmount()
            );
        });
    }
}
