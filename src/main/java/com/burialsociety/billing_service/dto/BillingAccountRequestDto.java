package com.burialsociety.billing_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BillingAccountRequestDto {
    private Long memberId;
    private String billingFrequency;
    private BigDecimal baseContributionAmount;
    private Integer gracePeriodDays;
    private String arrearsPolicy;
    private Boolean paymentHoliday;
    private LocalDate lastPaymentDate;
    private BigDecimal currentBalance;
    private LocalDate nextBillingDate;
    private String accountStatus;


}
