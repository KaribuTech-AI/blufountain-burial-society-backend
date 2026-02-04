package com.burialsociety.billing_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentDto {
    private Long id;
    private String receiptNumber;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String referenceNumber;
}
