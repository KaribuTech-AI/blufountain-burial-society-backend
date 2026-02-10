package com.burialsociety.claims_service.service;

import com.burialsociety.claims_service.dto.*;
import java.math.BigDecimal;
import java.util.List;

public interface ClaimService {
    ClaimResponseDto submitClaim(ClaimRequestDto request);

    ClaimResponseDto getClaimById(Long id);

    ClaimResponseDto approveClaim(Long claimId, ClaimApprovalDto approvalDto);

    ClaimResponseDto rejectClaim(Long claimId, ClaimRejectionDto rejectionDto);

    List<ClaimResponseDto> getClaimsByMember(Long memberId);

    List<ClaimResponseDto> getClaimsByStatus(String status);

    List<ClaimResponseDto> getAllClaims();
}
