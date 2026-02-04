package com.burialsociety.claims_service.mapper;

import com.burialsociety.claims_service.dto.*;
import com.burialsociety.claims_service.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    @Mapping(target = "relationshipToMember", source = "relationship")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "claimNumber", ignore = true)
    @Mapping(target = "claimDate", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    @Mapping(target = "payoutAmount", ignore = true)
    @Mapping(target = "approvalNotes", ignore = true)
    Claim toEntity(ClaimRequestDto dto);

    @Mapping(target = "relationship", source = "relationshipToMember")
    @Mapping(target = "memberId", source = "member.id")
    @Mapping(target = "memberName", expression = "java(entity.getMember().getPersonalDetails().getFirstname() + \" \" + entity.getMember().getPersonalDetails().getLastname())")
    @Mapping(target = "history", source = "statusHistory")
    ClaimResponseDto toDto(Claim entity);

    ClaimStatusHistoryDto toDto(ClaimStatusHistory entity);
}
