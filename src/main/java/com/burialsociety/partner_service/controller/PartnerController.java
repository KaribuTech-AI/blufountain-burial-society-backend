package com.burialsociety.partner_service.controller;

import com.burialsociety.partner_service.entity.Partner;
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
    public ResponseEntity<List<Partner>> getAllPartners() {
        return ResponseEntity.ok(partnerService.getAllPartners());
    }

    @PostMapping
    public ResponseEntity<Partner> createPartner(@RequestBody Partner partner) {
        return ResponseEntity.ok(partnerService.createPartner(partner));
    }
}
