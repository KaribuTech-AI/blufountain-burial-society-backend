package com.burialsociety.claims_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_status_history", schema = "claims")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime changeDate;

    private String changedBy; // User ID

    private String remarks;
}
