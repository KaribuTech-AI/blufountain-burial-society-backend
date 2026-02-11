package com.burialsociety.partner_service.controller;

import com.burialsociety.partner_service.dto.PartnerRequestDto;
import com.burialsociety.partner_service.dto.PartnerResponseDto;
import com.burialsociety.partner_service.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/partners")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PartnerController {

    private final PartnerService partnerService;

    @GetMapping
    public ResponseEntity<List<PartnerResponseDto>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponseDto> getPartnerById(@PathVariable Long id) {
        return ResponseEntity.ok(partnerService.getPartnerById(id));
    }

    @PostMapping
    public ResponseEntity<PartnerResponseDto> createPartner(@RequestBody PartnerRequestDto requestDto) {
        return ResponseEntity.ok(partnerService.createPartner(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartnerResponseDto> updatePartner(@PathVariable Long id,
            @RequestBody PartnerRequestDto requestDto) {
        return ResponseEntity.ok(partnerService.updatePartner(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }
}
