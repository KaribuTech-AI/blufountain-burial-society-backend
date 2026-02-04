package com.burialsociety.member_service.service.impl;

import com.burialsociety.member_service.dto.MemberRequestDto;
import com.burialsociety.member_service.dto.MemberResponseDto;
import com.burialsociety.member_service.dto.PersonalDetailsRequestDto;
import com.burialsociety.member_service.dto.PersonalDetailsResponseDto;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.entity.PersonalDetails;
import com.burialsociety.member_service.exception.ResourceNotFoundException;
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
    // We'll manually map DTOs for clarity. You can use a library
    // like ModelMapper or MapStruct for complex objects.

    @Override
    @Transactional
    public MemberResponseDto createMember(MemberRequestDto requestDto) {

        // 1. Build the Member
        Member member = Member.builder()
      .caseNumber(requestDto.getCaseNumber())
                .createdBy(requestDto.getCreatedBy())
                .build();

        // 2. Build the PersonalDetails
        PersonalDetailsRequestDto pdRequest = requestDto.getPersonalDetails();
        PersonalDetails personalDetails = PersonalDetails.builder()
                .nationality(pdRequest.getNationality())
                .citizenship(pdRequest.getCitizenship())
                .identificationType(pdRequest.getIdentificationType())
                .idNumber(pdRequest.getIdNumber())
                .dateOfBirth(pdRequest.getDateOfBirth())
                .gender(pdRequest.getGender())
                .title(pdRequest.getTitle())
                .firstname(pdRequest.getFirstname())
                .middlename(pdRequest.getMiddlename())
                .lastname(pdRequest.getLastname())
                .maidenName(pdRequest.getMaidenName())
                .maritalStatus(pdRequest.getMaritalStatus())
                .religion(pdRequest.getReligion())
                .race(pdRequest.getRace())
                .numberOfDependents(pdRequest.getNumberOfDependents())
                .numberOfOtherDependents(pdRequest.getNumberOfOtherDependents())
                .passportNumber(pdRequest.getPassportNumber())
                .passportExpiryDate(pdRequest.getPassportExpiryDate())
                .driverLicenseNumber(pdRequest.getDriverLicenseNumber())
                .birthDistrict(pdRequest.getBirthDistrict())
                // .member(member) // <-- We set this using the helper method
                .build();

        // 3. Link them using our helper method
        member.setPersonalDetails(personalDetails);

        // 4. Save the Member (JPA will also save PersonalDetails
        //    because of CascadeType.ALL)
        Member savedMember = memberRepository.save(member);

        // 5. Convert and return
        return convertToResponseDto(savedMember);
    }

    @Override
    @Transactional
    public MemberResponseDto updateMember(Long id, MemberRequestDto requestDto) {

        // 1. Find the existing member
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));

        // 2. Update the simple Member fields
        member.setCaseNumber(requestDto.getCaseNumber());

        // 3. Update the nested PersonalDetails
        if (requestDto.getPersonalDetails() != null) {
            PersonalDetails pdEntity = member.getPersonalDetails();
            PersonalDetailsRequestDto pdRequest = requestDto.getPersonalDetails();

            // This check is for data integrity, though with our create flow
            // it should never be null.
            if (pdEntity == null) {
                throw new ResourceNotFoundException("No PersonalDetails found for member id: " + id);
            }

            // Map all fields from request DTO to the entity
            pdEntity.setNationality(pdRequest.getNationality());
            pdEntity.setCitizenship(pdRequest.getCitizenship());
            pdEntity.setIdentificationType(pdRequest.getIdentificationType());
            pdEntity.setIdNumber(pdRequest.getIdNumber());
            pdEntity.setDateOfBirth(pdRequest.getDateOfBirth());
            pdEntity.setGender(pdRequest.getGender());
            pdEntity.setTitle(pdRequest.getTitle());
            pdEntity.setFirstname(pdRequest.getFirstname());
            pdEntity.setMiddlename(pdRequest.getMiddlename());
            pdEntity.setLastname(pdRequest.getLastname());
            pdEntity.setMaidenName(pdRequest.getMaidenName());
            pdEntity.setMaritalStatus(pdRequest.getMaritalStatus());
            pdEntity.setReligion(pdRequest.getReligion());
            pdEntity.setRace(pdRequest.getRace());
            pdEntity.setNumberOfDependents(pdRequest.getNumberOfDependents());
            pdEntity.setNumberOfOtherDependents(pdRequest.getNumberOfOtherDependents());
            pdEntity.setPassportNumber(pdRequest.getPassportNumber());
            pdEntity.setPassportExpiryDate(pdRequest.getPassportExpiryDate());
            pdEntity.setDriverLicenseNumber(pdRequest.getDriverLicenseNumber());
            pdEntity.setBirthDistrict(pdRequest.getBirthDistrict());
        }

        // 4. Save the updated Member. CascadeType.ALL saves PersonalDetails.
        Member updatedMember = memberRepository.save(member);

        // 5. Return the updated DTO
        return convertToResponseDto(updatedMember);
    }

    @Override
    public MemberResponseDto getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return convertToResponseDto(member);
    }

    @Override
    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMember(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }
        memberRepository.deleteById(id);
    }

//    @Override
//    public MemberResponseDto updateMember(Long id, MemberRequestDto requestDto) {
//        // 1. Find the existing member
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
//
//        // 2. Update the fields
//        // Note: 'createdBy' and 'creationDate' are typically not updated.
//        member.setCaseNumber(requestDto.getCaseNumber());
//
//        // 3. Save the updated member
//        Member updatedMember = memberRepository.save(member);
//
//        // 4. Return the updated DTO
//        return convertToResponseDto(updatedMember);
//    }

    // --- Helper Method for DTO Conversion ---
    private MemberResponseDto convertToResponseDto(Member member) {
        MemberResponseDto dto = new MemberResponseDto();
        dto.setId(member.getId());
        dto.setCaseNumber(member.getCaseNumber());
        dto.setCreatedBy(member.getCreatedBy());
        dto.setCreationDate(member.getCreationDate());

        if (member.getPersonalDetails() != null) {
            PersonalDetails pd = member.getPersonalDetails();
            PersonalDetailsResponseDto pdDto = new PersonalDetailsResponseDto();
            pdDto.setId(pd.getId());
            pdDto.setFirstname(pd.getFirstname());
            pdDto.setLastname(pd.getLastname());
            pdDto.setIdNumber(pd.getIdNumber());
            pdDto.setDateOfBirth(pd.getDateOfBirth());
            pdDto.setGender(pd.getGender());

            dto.setPersonalDetails(pdDto);
        }


        return dto;
    }


}

