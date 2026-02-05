package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employment_details", schema = "member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // Current Employer
    @Column(name = "employer_name")
    private String employerName;

    @Column(name = "job_title")
    private String jobTitle;

    private String industry;

    @Column(name = "industry_sub_category")
    private String industrySubCategory;

    @Column(name = "monthly_gross_income")
    private BigDecimal monthlyGrossIncome;

    @Column(name = "monthly_net_income")
    private BigDecimal monthlyNetIncome;

    @Column(name = "salary_currency")
    private String salaryCurrency; // USD, ZWG

    @Column(name = "employment_type")
    private String employmentType; // PERMANENT, CONTRACT

    @Column(name = "employer_type")
    private String employerType;

    @Column(name = "employment_date")
    private LocalDate employmentDate;

    @Column(name = "employment_end_date")
    private LocalDate employmentEndDate;

    // Previous Employer (Flat fields as per frontend or separate entity? Sticking
    // to flat for simplicity as per Plan)
    @Column(name = "previous_employer_name")
    private String previousEmployerName;

    // Contact info for employer
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    private String email;
    private String address;

    @Column(name = "customer_risk_status")
    private String customerRiskStatus;

    @OneToMany(mappedBy = "employmentDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SourceOfFunds> sourceOfFunds;

    // Config method
    public void setSourceOfFunds(List<SourceOfFunds> sourceOfFunds) {
        if (sourceOfFunds != null) {
            sourceOfFunds.forEach(s -> s.setEmploymentDetails(this));
        }
        this.sourceOfFunds = sourceOfFunds;
    }
}
