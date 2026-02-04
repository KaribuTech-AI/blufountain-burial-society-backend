package com.burialsociety.treasury_service.repository;

import com.burialsociety.treasury_service.entity.GLAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GLAccountRepository extends JpaRepository<GLAccount, Long> {
}
