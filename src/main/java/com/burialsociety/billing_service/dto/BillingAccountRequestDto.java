package com.burialsociety.billing_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class BillingAccountRequestDto {
    private Long memberId;
    private String billingFrequency;
    private BigDecimal baseContributionAmount;
    private Integer gracePeriodDays;
    private String arrearsPolicy;
    private Boolean paymentHoliday;
}
