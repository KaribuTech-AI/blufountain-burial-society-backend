package com.burialsociety.member_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class PersonalDetailsRequestDto {

    private String nationality;
    private String citizenship;
    private String identificationType;

    // Adding validation based on your schema
    @NotEmpty(message = "First name is required")
    private String firstname;

    @NotEmpty(message = "Last name is required")
    private String lastname;

    @NotEmpty(message = "ID number is required")
    private String idNumber;

    private String title;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth; // 'DATE' maps to LocalDate

    private String middlename;

    @NotEmpty(message = "Gender is required")
    private String gender;

    private String maidenName;
    private String maritalStatus;
    private String religion;
    private String race;
    private Integer numberOfDependents;
    private Integer numberOfOtherDependents;
    private String passportNumber;
    private LocalDate passportExpiryDate;
    private String driverLicenseNumber;
    private String birthDistrict;

}
