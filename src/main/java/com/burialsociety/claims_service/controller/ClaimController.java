package com.burialsociety.claims_service.controller;

import com.burialsociety.claims_service.dto.*;
import com.burialsociety.claims_service.service.ClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/claims")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<ClaimResponseDto> submitClaim(@RequestBody ClaimRequestDto request) {
        return ResponseEntity.ok(claimService.submitClaim(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponseDto> getClaim(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ClaimResponseDto> approveClaim(
            @PathVariable Long id,
            @RequestBody ClaimApprovalDto approvalDto) {
        return ResponseEntity.ok(claimService.approveClaim(id, approvalDto));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ClaimResponseDto> rejectClaim(
            @PathVariable Long id,
            @RequestBody ClaimRejectionDto rejectionDto) {
        return ResponseEntity.ok(claimService.rejectClaim(id, rejectionDto));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ClaimResponseDto>> getMemberClaims(@PathVariable Long memberId) {
        return ResponseEntity.ok(claimService.getClaimsByMember(memberId));
    }

    @GetMapping
    public ResponseEntity<List<ClaimResponseDto>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }
}
