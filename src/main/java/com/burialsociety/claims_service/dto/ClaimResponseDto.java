package com.burialsociety.claims_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ClaimResponseDto {
    private Long id;
    private String claimNumber;
    private Long memberId;
    private String memberName; // Helpful for UI
    private LocalDate claimDate;
    private String deceasedName;
    private String relationship;
    private String claimType;
    private String status;
    private BigDecimal payoutAmount;
    private String approvalNotes;
    private List<ClaimStatusHistoryDto> history;
}
