package com.burialsociety.member_service.service.impl;

import com.burialsociety.member_service.entity.BusinessRule;
import com.burialsociety.member_service.repository.BusinessRuleRepository;
import com.burialsociety.member_service.service.BusinessRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessRuleServiceImpl implements BusinessRuleService {
    private final BusinessRuleRepository businessRuleRepository;

    @Override
    public List<BusinessRule> getAllRules() {
        return businessRuleRepository.findAll();
    }

    @Override
    public BusinessRule getRuleById(Long id) {
        return businessRuleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
    }

    @Override
    public BusinessRule createRule(BusinessRule rule) {
        if (rule.getStatus() == null) {
            rule.setStatus("DRAFT");
        }
        return businessRuleRepository.save(rule);
    }

    @Override
    public BusinessRule updateRule(Long id, BusinessRule rule) {
        BusinessRule existing = getRuleById(id);
        existing.setName(rule.getName());
        existing.setCondition(rule.getCondition());
        existing.setOutput(rule.getOutput());
        existing.setEffectiveDate(rule.getEffectiveDate());
        existing.setStatus(rule.getStatus());
        return businessRuleRepository.save(existing);
    }

    @Override
    public void deleteRule(Long id) {
        businessRuleRepository.deleteById(id);
    }

    @Override
    public void activateRule(Long id) {
        BusinessRule rule = getRuleById(id);
        rule.setStatus("ACTIVE");
        businessRuleRepository.save(rule);
    }
}
