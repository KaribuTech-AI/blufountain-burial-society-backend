package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.PlanAddOn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanAddOnRepository extends JpaRepository<PlanAddOn, Long> {
}
