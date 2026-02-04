package com.burialsociety.billing_service.entity;

import com.burialsociety.member_service.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "billing_accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    @Builder.Default
    private BigDecimal currentBalance = BigDecimal.ZERO; // Positive means credit, Negative means Arrears (or vice versa? Usually Debit is + owed). Let's say + is Owed.

    @Column(nullable = false)
    private String billingFrequency; // MONTHLY, ANNUALLY

    @Column(nullable = false)
    private BigDecimal baseContributionAmount;

    private Integer gracePeriodDays;
    
    private String arrearsPolicy; // WARN, SUSPEND, TERMINATE

    private LocalDate nextBillingDate;
    
    private LocalDate lastPaymentDate;

    // Status: ACTIVE, IN_ARREARS, LAPSED, SUSPENDED
    @Column(nullable = false)
    private String accountStatus;

    @OneToMany(mappedBy = "billingAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "billingAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;
}
