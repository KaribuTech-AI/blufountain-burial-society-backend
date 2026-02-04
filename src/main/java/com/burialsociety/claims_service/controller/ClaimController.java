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
            @RequestParam BigDecimal amount, 
            @RequestParam String notes,
            @RequestParam(required = false, defaultValue = "Admin") String approverId) {
        return ResponseEntity.ok(claimService.approveClaim(id, amount, notes, approverId));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ClaimResponseDto> rejectClaim(
            @PathVariable Long id, 
            @RequestParam String notes,
            @RequestParam(required = false, defaultValue = "Admin") String approverId) {
        return ResponseEntity.ok(claimService.rejectClaim(id, notes, approverId));
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<ClaimResponseDto>> getMemberClaims(@PathVariable Long memberId) {
        return ResponseEntity.ok(claimService.getClaimsByMember(memberId));
    }
}
