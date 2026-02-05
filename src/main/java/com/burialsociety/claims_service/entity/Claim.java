package com.burialsociety.claims_service.entity;

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
@Table(name = "claims", schema = "claims")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false, unique = true)
    private String claimNumber;

    @Column(nullable = false)
    private LocalDate claimDate;

    // Deceased Details
    @Column(nullable = false)
    private String deceasedName;

    @Column(nullable = false)
    private String relationshipToMember; // PRINCIPAL, SPOUSE, etc.

    @Column(nullable = false)
    private LocalDate dateOfDeath;

    // Claim Details
    @Column(nullable = false)
    private String claimType; // CASH, TOMBSTONE, etc.

    private String submissionChannel; // WALK_IN, EMAIL, etc.

    private String notes;

    // Financials
    private BigDecimal payoutAmount;

    // Status
    @Column(nullable = false)
    private String status; // LOGGED, VERIFIED, APPROVED, REJECTED, PAID

    private String approvalNotes;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClaimStatusHistory> statusHistory;
}
