package com.burialsociety.treasury_service.service;

import com.burialsociety.treasury_service.entity.GLAccount;
import com.burialsociety.treasury_service.entity.JournalEntry;
import java.util.List;

public interface TreasuryService {
    List<GLAccount> getAllAccounts();
    GLAccount createAccount(GLAccount account);
    
    List<JournalEntry> getAllJournals();
    JournalEntry postJournal(JournalEntry entry);

    List<com.burialsociety.treasury_service.entity.FinancialPeriod> getAllPeriods();
    void closePeriod(Long periodId);
}
