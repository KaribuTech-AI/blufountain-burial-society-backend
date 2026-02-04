package com.burialsociety.treasury_service.service;

import com.burialsociety.treasury_service.entity.GLAccount;
import com.burialsociety.treasury_service.repository.GLAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TreasuryService {
    private final GLAccountRepository glAccountRepository;

    public List<GLAccount> getAllAccounts() {
        return glAccountRepository.findAll();
    }

    public GLAccount createAccount(GLAccount account) {
        return glAccountRepository.save(account);
    }
}
