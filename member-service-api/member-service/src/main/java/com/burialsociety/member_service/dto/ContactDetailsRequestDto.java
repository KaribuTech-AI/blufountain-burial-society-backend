package com.burialsociety.member_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ContactDetailsRequestDto {
    @Email(message = "Email must be a valid address")
    private String email;

    @NotEmpty(message = "Phone number is required")
    private String phoneNumber;

    private String telephoneNumber;
    private String socialMediaHandle;
}
