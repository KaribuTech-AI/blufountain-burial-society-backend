package com.burialsociety.claims_service.service;

import com.burialsociety.claims_service.dto.*;
import java.math.BigDecimal;
import java.util.List;

public interface ClaimService {
    ClaimResponseDto submitClaim(ClaimRequestDto request);
    ClaimResponseDto getClaimById(Long id);
    ClaimResponseDto approveClaim(Long claimId, java.math.BigDecimal amount, String notes, String approverId);
    ClaimResponseDto rejectClaim(Long claimId, String notes, String approverId);
    List<ClaimResponseDto> getClaimsByMember(Long memberId);
    List<ClaimResponseDto> getClaimsByStatus(String status);
    List<ClaimResponseDto> getAllClaims();
}
