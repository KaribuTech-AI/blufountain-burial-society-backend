package com.burialsociety.claims_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ClaimApprovalDto {
    private BigDecimal amount;
    private String notes;
    private String approverId;
}
