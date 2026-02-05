package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.BusinessRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRuleRepository extends JpaRepository<BusinessRule, Long> {
    List<BusinessRule> findByStatus(String status);
}
