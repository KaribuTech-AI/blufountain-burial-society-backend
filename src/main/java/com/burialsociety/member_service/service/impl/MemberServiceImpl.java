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
        // Convert DTO to Entity
        Member member = memberMapper.toEntity(requestDto);


        syncRelationships(member);
        Member savedMember = memberRepository.save(member);

        // Convert back to DTO
        return memberMapper.toDto(savedMember);
    }

    @Override
    @Transactional
    public MemberResponseDto updateMember(Long id, MemberRequestDto requestDto) {
        Member existingMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        // Map the new data to a fresh entity object
        Member updatedEntity = memberMapper.toEntity(requestDto);

        // Preserve the identity and metadata
        updatedEntity.setId(existingMember.getId());
        updatedEntity.setCreationDate(existingMember.getCreationDate());

        // Sync relationships for the updated entity
        syncRelationships(updatedEntity);

        Member saved = memberRepository.save(updatedEntity);
        return memberMapper.toDto(saved);
    }

    /**
     * Helper method to ensure bidirectional relationships are established.
     * JPA needs the child to have a reference to the parent for the 'member_id' column.
     */
    private void syncRelationships(Member member) {
        if (member.getPersonalDetails() != null) {
            member.getPersonalDetails().setMember(member);
        }

        if (member.getContactDetails() != null) {
            member.getContactDetails().setMember(member);
        }

        if (member.getAddressDetails() != null) {
            member.getAddressDetails().forEach(address -> address.setMember(member));
        }

        if (member.getCitizenship() != null) {
            member.getCitizenship().setMember(member);
        }

        if (member.getEmploymentDetails() != null) {
            member.getEmploymentDetails().setMember(member);

            // Link each SourceOfFunds to the EmploymentDetails parent
            if (member.getEmploymentDetails().getSourceOfFunds() != null) {
                member.getEmploymentDetails().getSourceOfFunds().forEach(source ->
                        source.setEmploymentDetails(member.getEmploymentDetails())
                );
            }
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