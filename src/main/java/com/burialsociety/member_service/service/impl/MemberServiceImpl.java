package com.burialsociety.member_service.service.impl;

import com.burialsociety.member_service.dto.MemberRequestDto;
import com.burialsociety.member_service.dto.MemberResponseDto;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.exception.ResourceNotFoundException;
import com.burialsociety.member_service.mapper.MemberMapper;
import com.burialsociety.member_service.repository.MemberRepository;
import com.burialsociety.member_service.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    @Transactional
    public MemberResponseDto createMember(MemberRequestDto requestDto) {
        // 1. Convert DTO to Entity
        Member member = memberMapper.toEntity(requestDto);

        // 2. Establish bidirectional relationships before saving
        // This is crucial for JPA to populate 'member_id' in child tables
        syncRelationships(member);

        // 3. Save the Member (CascadeType.ALL will save the children)
        Member savedMember = memberRepository.save(member);

        // 4. Convert back to DTO
        return memberMapper.toDto(savedMember);
    }

    @Override
    @Transactional
    public MemberResponseDto updateMember(Long id, MemberRequestDto requestDto) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        // Map the new data to a fresh entity object
        Member updatedEntity = memberMapper.toEntity(requestDto);

        // Preserve the identity and audit metadata from the existing record
        updatedEntity.setId(existingMember.getId());
        updatedEntity.setCreatedBy(existingMember.getCreatedBy());
        updatedEntity.setCreationDate(existingMember.getCreationDate());

        // Preserve case number if typically auto-generated and not in DTO
        if (updatedEntity.getCaseNumber() == null) {
            updatedEntity.setCaseNumber(existingMember.getCaseNumber());
        }

        // Sync relationships for the updated entity
        syncRelationships(updatedEntity);

        // Save (merge) the updated entity
        Member saved = memberRepository.save(updatedEntity);
        return memberMapper.toDto(saved);
    }

    /**
     * Helper method to ensure bidirectional relationships are established.
     * JPA needs the child to have a reference to the parent for the 'member_id' column.
     */
    private void syncRelationships(Member member) {
        // 1. Personal Details
        if (member.getPersonalDetails() != null) {
            member.getPersonalDetails().setMember(member);
        }

        // 2. Contact Details
        if (member.getContactDetails() != null) {
            member.getContactDetails().setMember(member);
        }

        // 3. Address Details
        if (member.getAddressDetails() != null) {
            member.getAddressDetails().forEach(address -> address.setMember(member));
        }

        // 4. Citizenship
        if (member.getCitizenship() != null) {
            member.getCitizenship().setMember(member);
        }

        // 5. Employment Details & Source of Funds
        if (member.getEmploymentDetails() != null) {
            member.getEmploymentDetails().setMember(member);

            // Nested relationship: Source of Funds -> Employment Details
            if (member.getEmploymentDetails().getSourceOfFunds() != null) {
                member.getEmploymentDetails().getSourceOfFunds().forEach(source ->
                        source.setEmploymentDetails(member.getEmploymentDetails())
                );
            }
        }

        // 6. Documents
        if (member.getDocuments() != null) {
            member.getDocuments().forEach(doc -> doc.setMember(member));
        }

        // 7. Related Parties
        if (member.getRelatedParties() != null) {
            member.getRelatedParties().forEach(party -> party.setMember(member));
        }

        // 8. Membership Plan & Add-ons
        if (member.getMembershipPlan() != null) {
            member.getMembershipPlan().setMember(member);

            // Nested relationship: AddOns -> Membership Plan
            if (member.getMembershipPlan().getAddOns() != null) {
                member.getMembershipPlan().getAddOns().forEach(addOn ->
                        addOn.setMembershipPlan(member.getMembershipPlan()));
            }
        }

        // 9. Preferences
        if (member.getPreferences() != null) {
            member.getPreferences().setMember(member);
        }
    }

    @Override
    public MemberResponseDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return memberMapper.toDto(member);
    }

    @Override
    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }
}