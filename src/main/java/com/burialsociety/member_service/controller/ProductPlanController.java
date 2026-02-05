package com.burialsociety.member_service.controller;

import com.burialsociety.member_service.entity.ProductPlan;
import com.burialsociety.member_service.service.ProductPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-plans")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductPlanController {
    private final ProductPlanService productPlanService;

    @GetMapping
    public ResponseEntity<List<ProductPlan>> getAllPlans() {
        return ResponseEntity.ok(productPlanService.getAllPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductPlan> getPlanById(@PathVariable Long id) {
        return ResponseEntity.ok(productPlanService.getPlanById(id));
    }

    @PostMapping
    public ResponseEntity<ProductPlan> createPlan(@RequestBody ProductPlan plan) {
        return ResponseEntity.ok(productPlanService.createPlan(plan));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductPlan> updatePlan(@PathVariable Long id, @RequestBody ProductPlan plan) {
        return ResponseEntity.ok(productPlanService.updatePlan(id, plan));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        productPlanService.deletePlan(id);
        return ResponseEntity.ok().build();
    }
}
