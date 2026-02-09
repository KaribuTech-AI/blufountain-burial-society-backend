package com.burialsociety.member_service.controller;

import com.burialsociety.member_service.dto.MemberRequestDto;
import com.burialsociety.member_service.dto.MemberResponseDto;
import com.burialsociety.member_service.service.MemberService;
import org.springframework.transaction.annotation.Transactional; // Use Spring's Transactional for readOnly support
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@Valid @RequestBody MemberRequestDto requestDto) {
        // @Valid triggers the validation from the DTO
        MemberResponseDto createdMember = memberService.createMember(requestDto);
        return new ResponseEntity<>(createdMember, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<MemberResponseDto> getMemberById(@PathVariable Long id) {
        MemberResponseDto member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        List<MemberResponseDto> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}