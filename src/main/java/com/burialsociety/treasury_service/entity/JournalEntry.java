package com.burialsociety.treasury_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "journal_entries", schema = "treasury")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String journalId;
    private LocalDate entryDate;
    private String debitAccount;
    private String creditAccount;
    private BigDecimal amount;
    private String reference;
    private String postedBy;
}
