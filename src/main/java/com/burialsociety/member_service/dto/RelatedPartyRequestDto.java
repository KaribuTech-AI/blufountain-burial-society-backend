package com.burialsociety.member_service.dto;

import lombok.Data;

@Data
public class RelatedPartyRequestDto {
    private String relationshipType;
    private String firstname;
    private String lastname;
    private Boolean sameAddress;
    private Boolean sameEmployer;
    private String isAnExistingCustomer;
}
