package com.burialsociety.member_service.service;

import com.burialsociety.member_service.entity.BusinessRule;
import java.util.List;

public interface BusinessRuleService {
    List<BusinessRule> getAllRules();

    BusinessRule getRuleById(Long id);

    BusinessRule createRule(BusinessRule rule);

    BusinessRule updateRule(Long id, BusinessRule rule);

    void deleteRule(Long id);

    void activateRule(Long id);
}
