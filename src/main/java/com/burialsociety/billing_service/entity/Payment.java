package com.burialsociety.billing_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "billing_account_id", nullable = false)
    private BillingAccount billingAccount;

    @Column(nullable = false, unique = true)
    private String receiptNumber;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String paymentMethod; // CASH, ECOCASH, BANK_TRANSFER

    private String referenceNumber; // Bank Ref / Transaction ID

    private String collectedBy; // UserId
}
