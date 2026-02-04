package com.burialsociety.member_service.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PlanAddOnRequestDto {
    private String name;
    private BigDecimal price;
}
