package com.ColPlat.Backend.model.dto.request;

import com.ColPlat.Backend.model.entity.ReceivingTicket;
import com.ColPlat.Backend.model.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceivingTicketApprovalRequest {

    private Long receivingTicketId;

    private Long executedById;

    private String comment;

    private Long newStorageLocationId;
}
