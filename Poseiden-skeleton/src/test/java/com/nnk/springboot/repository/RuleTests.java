package com.nnk.springboot.repository;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RuleTests {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Test
	void ruleTest() {
		var rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");

		// Save
		rule = ruleNameRepository.save(rule);
		Assertions.assertNotNull(rule.getId());
        Assertions.assertEquals("Rule Name", rule.getName());

		// Update
		rule.setName("Rule Name Update");
		rule = ruleNameRepository.save(rule);
        Assertions.assertEquals("Rule Name Update", rule.getName());

		// Find
		final var listResult = ruleNameRepository.findAll();
        Assertions.assertFalse(listResult.isEmpty());

		// Delete
		final var id = rule.getId();
		ruleNameRepository.delete(rule);
		final var ruleList = ruleNameRepository.findById(id);
		Assertions.assertFalse(ruleList.isPresent());
	}
}
