package com.burialsociety.member_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String caseNumber;
    private String message;
    private String createdBy;
    private OffsetDateTime creationDate;
    private PersonalDetailsResponseDto personalDetails;
    private ContactDetailsRequestDto contactDetails; // Reusing RequestDto for simplicity if structure is same
    private List<AddressDetailsRequestDto> addressDetails;
    private CitizenshipRequestDto citizenship;
    private EmploymentDetailsRequestDto employmentDetails;
    private List<RelatedPartyRequestDto> relatedParties;
    private MembershipPlanRequestDto membershipPlan;
    private List<DocumentMetadataRequestDto> documents;
    private PreferencesRequestDto preferences;
}