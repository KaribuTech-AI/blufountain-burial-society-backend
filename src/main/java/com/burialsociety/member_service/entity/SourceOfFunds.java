package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "source_of_funds", schema = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceOfFunds {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_details_id", nullable = false)
    private EmploymentDetails employmentDetails;

    private String source;
    private String currency;
    private BigDecimal amount;
}
