package com.burialsociety.member_service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class EmploymentDetailsRequestDto {
    private String employerName;
    private String jobTitle;
    private String industry;
    private String industrySubCategory;
    private BigDecimal monthlyGrossIncome;
    private BigDecimal monthlyNetIncome;
    private String salaryCurrency;
    private String employmentType;
    private String employerType;
    private LocalDate employmentDate;
    private LocalDate employmentEndDate;
    private String previousEmployerName;
    private String phoneNumber;
    private String telephoneNumber;
    private String email;
    private String address;
    private String customerRiskStatus;
    
    private List<SourceOfFundsRequestDto> sourceOfFunds;
}
