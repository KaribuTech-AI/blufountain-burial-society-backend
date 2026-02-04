package com.burialsociety.member_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "address_type")
    private String addressType; // RESIDENTIAL, POSTAL

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "street_number")
    private String streetNumber;

    private String suburb;
    private String city;
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    // Additional fields from frontend
    @Column(name = "period_of_residence_years")
    private String periodOfResidenceInYears;

    @Column(name = "period_of_residence_months")
    private String periodOfResidenceInMonths;

    @Column(name = "value_of_property")
    private String valueOfProperty;

    @Column(name = "monthly_rental_amount")
    private String monthlyRentalAmount;

    @Column(name = "property_density")
    private String propertyDensity;
}
