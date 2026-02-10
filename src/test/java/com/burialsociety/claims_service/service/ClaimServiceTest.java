package com.burialsociety.claims_service.service;

import com.burialsociety.claims_service.dto.*;
import com.burialsociety.claims_service.entity.Claim;
import com.burialsociety.claims_service.entity.ClaimStatusHistory;
import com.burialsociety.claims_service.mapper.ClaimMapper;
import com.burialsociety.claims_service.repository.ClaimRepository;
import com.burialsociety.claims_service.service.impl.ClaimServiceImpl;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private ClaimMapper claimMapper;

    @InjectMocks
    private ClaimServiceImpl claimService;

    private Claim claim;
    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member();
        member.setId(1L);

        claim = new Claim();
        claim.setId(100L);
        claim.setMember(member);
        claim.setStatus("LOGGED");
        claim.setStatusHistory(new ArrayList<>());
    }

    @Test
    void submitClaim_Success() {
        ClaimRequestDto request = new ClaimRequestDto();
        request.setMemberId(1L);

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(claimMapper.toEntity(request)).thenReturn(claim);
        when(claimRepository.save(any(Claim.class))).thenReturn(claim);
        when(claimMapper.toDto(any(Claim.class))).thenReturn(new ClaimResponseDto());

        ClaimResponseDto response = claimService.submitClaim(request);

        assertNotNull(response);
        verify(claimRepository).save(any(Claim.class));
    }

    @Test
    void approveClaim_ShouldUpdateStatus() {
        when(claimRepository.findById(100L)).thenReturn(Optional.of(claim));
        when(claimRepository.save(any(Claim.class))).thenReturn(claim);
        when(claimMapper.toDto(any(Claim.class))).thenReturn(new ClaimResponseDto());

        ClaimApprovalDto approvalDto = new ClaimApprovalDto();
        approvalDto.setAmount(new BigDecimal("1500"));
        approvalDto.setNotes("Approved");
        approvalDto.setApproverId("Admin");

        claimService.approveClaim(100L, approvalDto);

        assertEquals("APPROVED", claim.getStatus());
        assertEquals(new BigDecimal("1500"), claim.getPayoutAmount());
        assertEquals(1, claim.getStatusHistory().size()); // 1 history item added
        verify(claimRepository).save(claim);
    }
}
