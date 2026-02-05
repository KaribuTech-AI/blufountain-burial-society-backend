package com.burialsociety.treasury_service.repository;

import com.burialsociety.treasury_service.entity.FinancialPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancialPeriodRepository extends JpaRepository<FinancialPeriod, Long> {
}
