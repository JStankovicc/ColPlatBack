package com.ColPlat.Backend.service;

import com.ColPlat.Backend.model.dto.request.ReceivingTicketApprovalRequest;
import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.ReceivingTicketApproval;
import org.springframework.transaction.annotation.Transactional;

public interface ReceivingTicketApprovalService {

    void create(ReceivingTicketApproval receivingTicketApproval);

    @Transactional
    void approve(String jwt, ReceivingTicketApprovalRequest receivingTicketApprovalRequest, ReceivingTicket receivingTicket);
}
