package com.burialsociety.billing_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InvoiceDto {
    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private BigDecimal amount;
    private BigDecimal amountPaid;
    private String status;
}
