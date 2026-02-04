package com.burialsociety.reporting_service.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class ReportSummaryDto {
    private long activeMembers;
    private double claimsRatio; // Claims / Members * 100
    private double collectionRate; // Payments / Invoices * 100
    private double avgClaimTurnaroundDays;
}
