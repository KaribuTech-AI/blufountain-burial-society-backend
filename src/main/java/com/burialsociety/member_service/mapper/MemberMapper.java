package com.burialsociety.member_service.mapper;

import com.burialsociety.member_service.dto.*;
import com.burialsociety.member_service.entity.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    Member toEntity(MemberRequestDto dto);

    MemberResponseDto toDto(Member entity);
    
    // Auto-map nested DTOs to Entities via naming convention
    // e.g. personalDetails -> personalDetails
    
    @AfterMapping
    default void linkRelationships(@MappingTarget Member member) {
        if (member.getPersonalDetails() != null) {
            member.getPersonalDetails().setMember(member);
        }
        if (member.getContactDetails() != null) {
            member.getContactDetails().setMember(member);
        }
        if (member.getAddressDetails() != null) {
            member.getAddressDetails().forEach(a -> a.setMember(member));
        }
        if (member.getCitizenship() != null) {
            member.getCitizenship().setMember(member);
        }
        if (member.getEmploymentDetails() != null) {
            member.getEmploymentDetails().setMember(member);
            if (member.getEmploymentDetails().getSourceOfFunds() != null) {
                member.getEmploymentDetails().getSourceOfFunds().forEach(s -> s.setEmploymentDetails(member.getEmploymentDetails()));
            }
        }
        if (member.getRelatedParties() != null) {
            member.getRelatedParties().forEach(p -> p.setMember(member));
        }
        if (member.getMembershipPlan() != null) {
            member.getMembershipPlan().setMember(member);
            if (member.getMembershipPlan().getAddOns() != null) {
                member.getMembershipPlan().getAddOns().forEach(a -> a.setMembershipPlan(member.getMembershipPlan()));
            }
        }
        if (member.getDocuments() != null) {
            member.getDocuments().forEach(d -> d.setMember(member));
        }
        if (member.getPreferences() != null) {
            member.getPreferences().setMember(member);
        }
    }
}
