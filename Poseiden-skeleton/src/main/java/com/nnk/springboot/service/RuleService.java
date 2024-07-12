package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.errors.UnknownRule;

import java.util.List;
import java.util.Optional;

/**
 * RuleService is responsible for managing the Rule data.
 */
public interface RuleService {

    /**
     * Save a new RuleName.
     * @param rule the RuleName to save
     */
    RuleName saveRule(RuleName rule);

    /**
     * Find all RuleName.
     * @return a list of RuleName
     */
    List<RuleName> findAllRules();

    /**
     * Find a RuleName by its id.
     * @param id the id of the RuleName to find
     * @return an Optional containing the RuleName if it exists
     */
    Optional<RuleName> findRuleById(Integer id);

    /**
     * Update a RuleName.
     * @param rule the RuleName to update
     * @throws UnknownRule if the RuleName is not found
     */
    RuleName updateRule(RuleName rule) throws UnknownRule;

    /**
     * Delete a RuleName by its id.
     * @param id the id of the RuleName to delete
     * @throws UnknownRule if the RuleName is not found
     */
    void deleteRuleById(Integer id) throws UnknownRule;
}
