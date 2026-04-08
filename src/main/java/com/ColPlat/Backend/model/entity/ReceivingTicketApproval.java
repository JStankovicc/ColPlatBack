package com.ColPlat.Backend.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_receiving_ticket_approval")
public class ReceivingTicketApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiving_ticket_id", nullable = false, unique = true)
    private ReceivingTicket receivingTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executed_by_user_id", nullable = false)
    private User executedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_user_id", nullable = false)
    private User approvedBy;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
