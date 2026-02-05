package com.burialsociety.treasury_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "financial_periods", schema = "treasury")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String periodName; // e.g. "January 2026"
    private String status; // "OPEN" or "CLOSED"
    private String closedBy;
    private LocalDate closedDate;
}
