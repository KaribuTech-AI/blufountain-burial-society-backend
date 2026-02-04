package com.burialsociety.member_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonalDetailsResponseDto {
    private Long id;
    private String nationality;
    private String citizenship;
    private String identificationType;
    private String idNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private String title;
    private String firstname;
    private String middlename;
    private String lastname;
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
