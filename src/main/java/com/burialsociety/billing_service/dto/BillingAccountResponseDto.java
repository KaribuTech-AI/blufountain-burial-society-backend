package com.burialsociety.billing_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BillingAccountResponseDto {
    private Long id;
    private Long memberId;
    private BigDecimal currentBalance;
    private String billingFrequency;
    private BigDecimal baseContributionAmount;
    private String accountStatus;
    private LocalDate nextBillingDate;
    
    // Optional summaries
    private List<InvoiceDto> recentInvoices;
    private List<PaymentDto> recentPayments;
}
