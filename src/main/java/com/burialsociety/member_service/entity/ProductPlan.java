package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Represents Product Plan Templates configured by administrators.
 * This is separate from MembershipPlan which represents a member's selected
 * plan.
 */
@Entity
@Table(name = "product_plans", schema = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Gold Family Plan"

    @Column(nullable = false)
    private BigDecimal premium; // Monthly premium

    @Column(name = "benefit_limit", nullable = false)
    private BigDecimal benefitLimit; // Maximum claim amount

    @Column(name = "waiting_period")
    private String waitingPeriod; // e.g., "6 Months"

    @Column(nullable = false)
    private String status; // ACTIVE or INACTIVE

    @Column(name = "grace_period_days")
    private Integer gracePeriodDays; // Default: 30

    @Column(name = "lapse_threshold_months")
    private Integer lapseThresholdMonths; // Default: 3
}
