package com.burialsociety.claims_service.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ClaimStatusHistoryDto {
    private String status;
    private LocalDateTime changeDate;
    private String changedBy;
    private String remarks;
}
