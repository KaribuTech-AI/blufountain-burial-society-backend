package com.burialsociety.member_service.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonalDetailsResponseDto {
    private Long id;
    private String nationality;
    private String citizenship;
    private String identificationType;
    private String firstname;
    private String lastname;
    private String idNumber;
    private String title;
    private LocalDate dateOfBirth;
    private String middlename;
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
    private String usaResident;
    private String usaCitizen;
    private String usaGreenCardHolder;
    private String highestLevelOfEducation;
    private String ssrNumber;
}
