package com.burialsociety.member_service.service;

import com.burialsociety.member_service.entity.ProductPlan;
import java.util.List;

public interface ProductPlanService {
    List<ProductPlan> getAllPlans();

    ProductPlan getPlanById(Long id);

    ProductPlan createPlan(ProductPlan plan);

    ProductPlan updatePlan(Long id, ProductPlan plan);

    void deletePlan(Long id);
}
