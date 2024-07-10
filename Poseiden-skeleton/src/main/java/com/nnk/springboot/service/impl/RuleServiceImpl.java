package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.errors.UnknownRule;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    private final RuleNameRepository ruleRepository;

    @Autowired
    public RuleServiceImpl(final RuleNameRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveRule(final RuleName rule) {
        ruleRepository.save(rule);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RuleName> findAllRules() {
        return ruleRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RuleName> findRuleById(final Integer id) {
        return ruleRepository.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRule(final RuleName rule) throws UnknownRule {
        if (!ruleRepository.existsById(rule.getId())) {
            throw new UnknownRule(rule.getId());
        } else {
            ruleRepository.save(rule);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRuleById(Integer id) throws UnknownRule {
        if (!ruleRepository.existsById(id)) {
            throw new UnknownRule(id);
        } else {
            ruleRepository.deleteById(id);
        }
    }
}
