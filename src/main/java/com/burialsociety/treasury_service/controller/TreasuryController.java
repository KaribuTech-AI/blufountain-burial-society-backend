package com.burialsociety.treasury_service.controller;

import com.burialsociety.treasury_service.entity.GLAccount;
import com.burialsociety.treasury_service.entity.JournalEntry;
import com.burialsociety.treasury_service.service.TreasuryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/treasury")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TreasuryController {

    private final TreasuryService treasuryService;

    @GetMapping
    public ResponseEntity<List<GLAccount>> getAllAccounts() {
        return ResponseEntity.ok(treasuryService.getAllAccounts());
    }

    @PostMapping("/accounts")
    public ResponseEntity<GLAccount> createAccount(@RequestBody GLAccount account) {
        return ResponseEntity.ok(treasuryService.createAccount(account));
    }

    @GetMapping("/journals")
    public ResponseEntity<List<JournalEntry>> getAllJournals() {
        return ResponseEntity.ok(treasuryService.getAllJournals());
    }

    @PostMapping("/journals")
    public ResponseEntity<JournalEntry> postJournal(@RequestBody JournalEntry entry) {
        return ResponseEntity.ok(treasuryService.postJournal(entry));
    }

    @GetMapping("/periods")
    public ResponseEntity<List<com.burialsociety.treasury_service.entity.FinancialPeriod>> getAllPeriods() {
        return ResponseEntity.ok(treasuryService.getAllPeriods());
    }

    @PutMapping("/periods/{id}/close")
    public ResponseEntity<Void> closePeriod(@PathVariable Long id) {
        treasuryService.closePeriod(id);
        return ResponseEntity.ok().build();
    }
}
