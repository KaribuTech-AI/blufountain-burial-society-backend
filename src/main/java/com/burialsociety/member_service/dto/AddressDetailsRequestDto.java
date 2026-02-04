package com.burialsociety.member_service.dto;

import lombok.Data;

@Data
public class AddressDetailsRequestDto {
    private String addressType;
    private String addressLine1;
    private String addressLine2;
    private String streetName;
    private String streetNumber;
    private String suburb;
    private String city;
    private String country;
    private String postalCode;
    private String periodOfResidenceInYears;
    private String periodOfResidenceInMonths;
    private String valueOfProperty;
    private String monthlyRentalAmount;
    private String propertyDensity;
}
