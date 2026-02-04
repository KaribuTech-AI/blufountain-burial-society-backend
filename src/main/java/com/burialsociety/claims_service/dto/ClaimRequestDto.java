package com.burialsociety.claims_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimRequestDto {
    private Long memberId;
    private LocalDate dateOfDeath;
    private String deceasedName;
    private String relationship;
    private String claimType;
    private String notes;
    private String submissionChannel;
}
