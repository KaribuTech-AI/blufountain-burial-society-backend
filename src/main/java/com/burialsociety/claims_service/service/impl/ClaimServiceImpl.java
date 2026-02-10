package com.burialsociety.claims_service.service.impl;

import com.burialsociety.claims_service.dto.*;
import com.burialsociety.claims_service.entity.Claim;
import com.burialsociety.claims_service.entity.ClaimStatusHistory;
import com.burialsociety.claims_service.mapper.ClaimMapper;
import com.burialsociety.claims_service.repository.ClaimRepository;
import com.burialsociety.claims_service.service.ClaimService;
import com.burialsociety.member_service.entity.Member;
import com.burialsociety.member_service.exception.ResourceNotFoundException;
import com.burialsociety.member_service.entity.DocumentMetadata;
import com.burialsociety.member_service.repository.DocumentMetadataRepository;
import com.burialsociety.member_service.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimServiceImpl implements ClaimService {

        private final ClaimRepository claimRepository;
        private final MemberRepository memberRepository;
        private final DocumentMetadataRepository documentMetadataRepository;
        private final ClaimMapper claimMapper;

        @Override
        @Transactional
        public ClaimResponseDto submitClaim(ClaimRequestDto request) {
                Member member = memberRepository.findById(request.getMemberId())
                                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

                Claim claim = claimMapper.toEntity(request);
                claim.setMember(member);
                claim.setClaimNumber("CLM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
                claim.setClaimDate(LocalDate.now());
                claim.setStatus("LOGGED");

                // Add History
                ClaimStatusHistory history = ClaimStatusHistory.builder()
                                .claim(claim)
                                .status("LOGGED")
                                .changeDate(LocalDateTime.now())
                                .changedBy("System")
                                .remarks("Claim submitted via " + request.getSubmissionChannel())
                                .build();

                List<ClaimStatusHistory> historyList = new ArrayList<>();
                historyList.add(history);
                claim.setStatusHistory(historyList);

                Claim saved = claimRepository.save(claim);
                return claimMapper.toDto(saved);
        }

        @Override
        public ClaimResponseDto getClaimById(Long id) {
                Claim claim = claimRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

                ClaimResponseDto dto = claimMapper.toDto(claim);

                // Fetch Documents
                List<DocumentMetadata> docs = documentMetadataRepository.findByClaimId(id);
                List<DocumentDto> docDtos = docs.stream().map(doc -> DocumentDto.builder()
                                .id(doc.getId())
                                .documentType(doc.getDocumentType())
                                .fileName(doc.getFileName())
                                .uploadDate(doc.getUploadDate())
                                // .url("/api/documents/download/" + doc.getId()) // Conceptual URL
                                .build())
                                .collect(Collectors.toList());

                dto.setDocuments(docDtos);

                return dto;
        }

        @Override
        @Transactional
        public ClaimResponseDto approveClaim(Long id, ClaimApprovalDto approvalDto) {
                Claim claim = claimRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

                claim.setStatus("APPROVED");
                claim.setPayoutAmount(approvalDto.getAmount());
                claim.setApprovalNotes(approvalDto.getNotes());

                ClaimStatusHistory history = ClaimStatusHistory.builder()
                                .claim(claim)
                                .status("APPROVED")
                                .changeDate(LocalDateTime.now())
                                .changedBy(approvalDto.getApproverId() != null ? approvalDto.getApproverId() : "Admin")
                                .remarks(approvalDto.getNotes())
                                .build();

                if (claim.getStatusHistory() == null) {
                        claim.setStatusHistory(new ArrayList<>());
                }
                claim.getStatusHistory().add(history);

                Claim saved = claimRepository.save(claim);
                return claimMapper.toDto(saved);
        }

        @Override
        @Transactional
        public ClaimResponseDto rejectClaim(Long id, ClaimRejectionDto rejectionDto) {
                Claim claim = claimRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Claim not found"));

                claim.setStatus("REJECTED");
                claim.setApprovalNotes(rejectionDto.getNotes());

                ClaimStatusHistory history = ClaimStatusHistory.builder()
                                .claim(claim)
                                .status("REJECTED")
                                .changeDate(LocalDateTime.now())
                                .changedBy(rejectionDto.getApproverId() != null ? rejectionDto.getApproverId()
                                                : "Admin")
                                .remarks(rejectionDto.getNotes())
                                .build();

                if (claim.getStatusHistory() == null) {
                        claim.setStatusHistory(new ArrayList<>());
                }
                claim.getStatusHistory().add(history);

                Claim saved = claimRepository.save(claim);
                return claimMapper.toDto(saved);
        }

        @Override
        public List<ClaimResponseDto> getClaimsByMember(Long memberId) {
                return claimRepository.findByMemberId(memberId).stream()
                                .map(claimMapper::toDto)
                                .collect(Collectors.toList());
        }

        @Override
        public List<ClaimResponseDto> getAllClaims() {
                return claimRepository.findAll().stream()
                                .map(claimMapper::toDto)
                                .collect(Collectors.toList());
        }

        @Override
        public List<ClaimResponseDto> getClaimsByStatus(String status) {
                return claimRepository.findByStatus(status).stream()
                                .map(claimMapper::toDto)
                                .collect(Collectors.toList());
        }
}
