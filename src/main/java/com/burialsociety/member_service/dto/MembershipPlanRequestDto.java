package com.burialsociety.member_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MembershipPlanRequestDto {
    private String planName;
    private String planId;
    private BigDecimal totalPrice;
    private List<PlanAddOnRequestDto> addOns;
}
