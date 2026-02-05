package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

/**
 * Represents Business Rules for automated policy lifecycle and claim decisions.
 */
@Entity
@Table(name = "business_rules")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // e.g., "Auto-Lapse Rule"

    @Column(nullable = false, length = 1000)
    private String condition; // e.g., "IF Arrears > 3 Months"

    @Column(nullable = false, length = 1000)
    private String output; // e.g., "SET Status = LAPSED"

    @Column(name = "effective_date")
    private LocalDate effectiveDate;

    @Column(nullable = false)
    private String status; // ACTIVE, DRAFT, INACTIVE
}
