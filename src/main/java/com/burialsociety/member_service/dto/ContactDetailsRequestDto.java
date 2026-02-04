package com.burialsociety.member_service.dto;

import lombok.Data;

@Data
public class ContactDetailsRequestDto {
    private String email;
    private String phoneNumber;
    private String telephoneNumber;
    private String facebook;
    private String twitter;
    private String linkedin;
    private String skype;
}
