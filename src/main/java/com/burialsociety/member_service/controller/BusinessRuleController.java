package com.burialsociety.member_service.controller;

import com.burialsociety.member_service.entity.BusinessRule;
import com.burialsociety.member_service.service.BusinessRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business-rules")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BusinessRuleController {
    private final BusinessRuleService businessRuleService;

    @GetMapping
    public ResponseEntity<List<BusinessRule>> getAllRules() {
        return ResponseEntity.ok(businessRuleService.getAllRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessRule> getRuleById(@PathVariable Long id) {
        return ResponseEntity.ok(businessRuleService.getRuleById(id));
    }

    @PostMapping
    public ResponseEntity<BusinessRule> createRule(@RequestBody BusinessRule rule) {
        return ResponseEntity.ok(businessRuleService.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessRule> updateRule(@PathVariable Long id, @RequestBody BusinessRule rule) {
        return ResponseEntity.ok(businessRuleService.updateRule(id, rule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        businessRuleService.deleteRule(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activateRule(@PathVariable Long id) {
        businessRuleService.activateRule(id);
        return ResponseEntity.ok().build();
    }
}
