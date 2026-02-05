package com.burialsociety.billing_service.repository;

import com.burialsociety.billing_service.entity.BillingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BillingAccountRepository extends JpaRepository<BillingAccount, Long> {
    Optional<BillingAccount> findByMemberId(Long memberId);
    
    java.util.List<BillingAccount> findByCurrentBalanceLessThan(java.math.BigDecimal amount);
}
