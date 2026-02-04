package com.burialsociety.member_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SourceOfFundsRequestDto {
    private String source;
    private String currency;
    private BigDecimal amount;
}
