package com.burialsociety.member_service.service;

import com.burialsociety.member_service.dto.*;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.mapper.MemberMapper;
import com.burialsociety.member_service.repository.MemberRepository;
import com.burialsociety.member_service.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper; // Mock the mapper

    @InjectMocks
    private MemberServiceImpl memberService;

    private MemberRequestDto requestDto;
    private Member memberEntity;
    private MemberResponseDto responseDto;

    @BeforeEach
    void setUp() {
        requestDto = new MemberRequestDto();
        requestDto.setCaseNumber("CASE123");
        
        PersonalDetailsRequestDto pd = new PersonalDetailsRequestDto();
        pd.setFirstname("John");
        pd.setLastname("Doe");
        requestDto.setPersonalDetails(pd);

        memberEntity = new Member();
        memberEntity.setId(1L);
        memberEntity.setCaseNumber("CASE123");

        responseDto = new MemberResponseDto();
        responseDto.setId(1L);
        responseDto.setCaseNumber("CASE123");
    }

    @Test
    void createMember_ShouldSaveAndReturnDto() {
        // Arrange
        when(memberMapper.toEntity(any(MemberRequestDto.class))).thenReturn(memberEntity);
        when(memberRepository.save(any(Member.class))).thenReturn(memberEntity);
        when(memberMapper.toDto(any(Member.class))).thenReturn(responseDto);

        // Act
        MemberResponseDto result = memberService.createMember(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(memberRepository).save(any(Member.class));
    }
}
