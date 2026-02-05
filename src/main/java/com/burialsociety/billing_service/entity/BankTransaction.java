package com.burialsociety.billing_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bank_transactions", schema = "billing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate bankDate;
    private String description;
    private BigDecimal amount;

    // MATCHED, UNMATCHED
    private String status;

    // Optional: Link to matched Payment ID for audit
    private Long matchedPaymentId;
}
