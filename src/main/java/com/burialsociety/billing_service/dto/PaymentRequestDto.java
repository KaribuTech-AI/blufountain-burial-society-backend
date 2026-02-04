package com.burialsociety.billing_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequestDto {
    private Long billingAccountId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String referenceNumber;
    private String collectedBy;
}
