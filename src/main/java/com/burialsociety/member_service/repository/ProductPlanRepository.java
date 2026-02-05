package com.burialsociety.member_service.repository;

import com.burialsociety.member_service.entity.ProductPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPlanRepository extends JpaRepository<ProductPlan, Long> {
    List<ProductPlan> findByStatus(String status);
}
