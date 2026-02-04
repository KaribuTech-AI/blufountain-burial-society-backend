package com.burialsociety.member_service.service;


import com.burialsociety.member_service.dto.MemberRequestDto;
import com.burialsociety.member_service.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {
    MemberResponseDto createMember(MemberRequestDto requestDto);
    MemberResponseDto getMemberById(Long id);
    List<MemberResponseDto> getAllMembers();
    void deleteMember(Long id);
    MemberResponseDto updateMember(Long id, MemberRequestDto requestDto);
}