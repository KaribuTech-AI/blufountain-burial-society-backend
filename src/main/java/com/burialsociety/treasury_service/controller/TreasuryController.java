package com.burialsociety.treasury_service.controller;

import com.burialsociety.treasury_service.entity.GLAccount;
import com.burialsociety.treasury_service.service.TreasuryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/treasury/accounts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TreasuryController {

    private final TreasuryService treasuryService;

    @GetMapping
    public ResponseEntity<List<GLAccount>> getAllAccounts() {
        return ResponseEntity.ok(treasuryService.getAllAccounts());
    }

    @PostMapping
    public ResponseEntity<GLAccount> createAccount(@RequestBody GLAccount account) {
        return ResponseEntity.ok(treasuryService.createAccount(account));
    }
}
