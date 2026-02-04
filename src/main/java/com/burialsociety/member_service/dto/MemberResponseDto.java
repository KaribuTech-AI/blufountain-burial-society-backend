package com.burialsociety.member_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    // This is what we send back to the client.
    private Long id;
    private String caseNumber;
    private String message;
    private String createdBy;
    private OffsetDateTime creationDate;
    private PersonalDetailsResponseDto personalDetails;
}