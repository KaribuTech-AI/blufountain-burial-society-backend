package com.burialsociety.claims_service.dto;

import lombok.Data;

@Data
public class ClaimRejectionDto {
    private String notes;
    private String approverId;
}
