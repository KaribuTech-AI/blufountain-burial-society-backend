package com.burialsociety.member_service.service.impl;

import com.burialsociety.member_service.entity.ProductPlan;
import com.burialsociety.member_service.repository.ProductPlanRepository;
import com.burialsociety.member_service.service.ProductPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPlanServiceImpl implements ProductPlanService {
    private final ProductPlanRepository productPlanRepository;

    @Override
    public List<ProductPlan> getAllPlans() {
        return productPlanRepository.findAll();
    }

    @Override
    public ProductPlan getPlanById(Long id) {
        return productPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    @Override
    public ProductPlan createPlan(ProductPlan plan) {
        return productPlanRepository.save(plan);
    }

    @Override
    public ProductPlan updatePlan(Long id, ProductPlan plan) {
        ProductPlan existing = getPlanById(id);
        existing.setName(plan.getName());
        existing.setPremium(plan.getPremium());
        existing.setBenefitLimit(plan.getBenefitLimit());
        existing.setWaitingPeriod(plan.getWaitingPeriod());
        existing.setStatus(plan.getStatus());
        existing.setGracePeriodDays(plan.getGracePeriodDays());
        existing.setLapseThresholdMonths(plan.getLapseThresholdMonths());
        return productPlanRepository.save(existing);
    }

    @Override
    public void deletePlan(Long id) {
        productPlanRepository.deleteById(id);
    }
}
