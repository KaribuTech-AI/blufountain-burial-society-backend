package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
}
