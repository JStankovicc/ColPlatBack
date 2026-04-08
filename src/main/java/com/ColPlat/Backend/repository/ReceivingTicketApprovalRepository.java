package com.ColPlat.Backend.repository;

import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.ReceivingTicketApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivingTicketApprovalRepository extends JpaRepository<ReceivingTicketApproval, Long> {
}
