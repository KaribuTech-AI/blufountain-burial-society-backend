package com.burialsociety.treasury_service.service.impl;

import com.burialsociety.treasury_service.entity.GLAccount;
import com.burialsociety.treasury_service.entity.JournalEntry;
import com.burialsociety.treasury_service.repository.GLAccountRepository;
import com.burialsociety.treasury_service.repository.JournalEntryRepository;
import com.burialsociety.treasury_service.service.TreasuryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TreasuryServiceImpl implements TreasuryService {
    private final GLAccountRepository glAccountRepository;
    private final JournalEntryRepository journalEntryRepository;
    private final com.burialsociety.treasury_service.repository.FinancialPeriodRepository financialPeriodRepository;

    @Override
    public List<GLAccount> getAllAccounts() {
        return glAccountRepository.findAll();
    }

    @Override
    public GLAccount createAccount(GLAccount account) {
        return glAccountRepository.save(account);
    }

    @Override
    public List<JournalEntry> getAllJournals() {
        return journalEntryRepository.findAllByOrderByEntryDateDesc();
    }

    @Override
    public JournalEntry postJournal(JournalEntry entry) {
        if(entry.getJournalId() == null) {
            entry.setJournalId("JNL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        return journalEntryRepository.save(entry);
    }

    @Override
    public List<com.burialsociety.treasury_service.entity.FinancialPeriod> getAllPeriods() {
        return financialPeriodRepository.findAll();
    }

    @Override
    public void closePeriod(Long periodId) {
        var period = financialPeriodRepository.findById(periodId).orElseThrow();
        period.setStatus("CLOSED");
        period.setClosedBy("ADMIN"); // Mock user for now
        period.setClosedDate(java.time.LocalDate.now());
        financialPeriodRepository.save(period);
        // In real world, we would also lock all journals for this date range
    }
}
