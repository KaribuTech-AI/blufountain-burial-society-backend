package com.burialsociety.treasury_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "gl_accounts", schema = "treasury")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GLAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code; // e.g., "1000"

    private String name; // "Cash on Hand"
    private String type; // ASSET, LIABILITY, INCOME, EXPENSE

    private BigDecimal balance;
    private String status; // ACTIVE
}
