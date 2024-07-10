package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.errors.UnknownRule;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.impl.RuleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class RuleServiceTests {

    @Autowired
    private RuleService ruleService;

    @MockBean
    private RuleNameRepository ruleRepository;

    @BeforeEach
    public void setup() {
        ruleRepository = Mockito.mock(RuleNameRepository.class);
        ruleService = new RuleServiceImpl(ruleRepository);
    }

    @Test
    void saveRule_shouldSaveRule_newRule() {
        // GIVEN a new RuleName
        final var rule = new RuleName();

        // WHEN saving the RuleName
        ruleService.saveRule(rule);

        // THEN the RuleName is saved
        Mockito.verify(ruleRepository).save(rule);
    }

    @Test
    void findAllRules_shouldReturnAllRules_noRule() {
        // GIVEN a list of RuleNames

        // WHEN finding all RuleNames
        ruleService.findAllRules();

        // THEN the findAll method is called
        Mockito.verify(ruleRepository).findAll();
    }

    @Test
    void findRuleById_shouldReturnRuleById_ruleExists() {
        // GIVEN an id
        final var id = 1;

        // WHEN finding the RuleName by its id
        Mockito.when(ruleRepository.findById(id)).thenReturn(Optional.of(new RuleName()));
        final var rule = ruleService.findRuleById(id);

        // THEN the RuleName is found
        Mockito.verify(ruleRepository).findById(id);
        // AND the RuleName is returned
        Assertions.assertTrue(rule.isPresent());
    }

    @Test
    void findRuleById_shouldReturnEmptyRuleById_ruleDoesNotExist() {
        // GIVEN an id
        final var id = 1;

        // WHEN finding the RuleName by its id
        Mockito.when(ruleRepository.findById(id)).thenReturn(Optional.empty());
        final var rule = ruleService.findRuleById(id);

        // THEN the RuleName is found
        Mockito.verify(ruleRepository).findById(id);
        // AND the RuleName is returned
        Assertions.assertTrue(rule.isEmpty());
    }

    @Test
    void updateRule_shouldUpdateRule_ruleExists() {
        // GIVEN a RuleName
        final var rule = new RuleName();

        // WHEN updating the RuleName
        Mockito.when(ruleRepository.existsById(rule.getId())).thenReturn(true);
        ruleService.updateRule(rule);

        // THEN the RuleName is updated
        Mockito.verify(ruleRepository).save(rule);
    }

    @Test
    void updateRule_shouldThrowException_ruleDoesNotExist() {
        // GIVEN a RuleName
        final var rule = new RuleName();

        // WHEN updating the RuleName
        Mockito.when(ruleRepository.existsById(rule.getId())).thenReturn(false);

        // THEN an exception is thrown
        Assertions.assertThrows(UnknownRule.class, () -> ruleService.updateRule(rule));
    }

    @Test
    void deleteRuleById_shouldDeleteRuleById_ruleExists() {
        // GIVEN an id
        final var id = 1;

        // WHEN deleting the RuleName by its id
        Mockito.when(ruleRepository.existsById(id)).thenReturn(true);
        ruleService.deleteRuleById(id);

        // THEN the RuleName is deleted
        Mockito.verify(ruleRepository).deleteById(id);
    }

    @Test
    void deleteRuleById_shouldThrowException_ruleDoesNotExist() {
        // GIVEN an id
        final var id = 1;

        // WHEN deleting the RuleName by its id
        Mockito.when(ruleRepository.existsById(id)).thenReturn(false);

        // THEN an exception is thrown
        Assertions.assertThrows(UnknownRule.class, () -> ruleService.deleteRuleById(id));
    }
}
