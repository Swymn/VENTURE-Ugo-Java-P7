package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RuleNameController {

    private static final String RULE_ATTRIBUTE = "ruleName";
    private static final String RULES_ATTRIBUTE = "ruleNames";

    private final RuleService ruleService;

    @Autowired
    public RuleNameController(final RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @RequestMapping("/ruleName/list")
    public String home(final Model model) {
        final var rules = ruleService.findAllRules();
        model.addAttribute(RULES_ATTRIBUTE, rules);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(final RuleName rule, final Model model) {
        model.addAttribute(RULE_ATTRIBUTE, rule);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid final RuleName ruleName, final BindingResult result, final Model model) {
        ruleService.saveRule(ruleName);
        model.addAttribute(RULE_ATTRIBUTE, ruleName);
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var rule = ruleService.findRuleById(id);

        if (rule.isEmpty()) {
            model.addAttribute("error", "Unknown rule.");
            return "ruleName/list";
        }

        model.addAttribute(RULE_ATTRIBUTE, rule.get());
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer id, @Valid final RuleName ruleName,
                                 final BindingResult result, final Model model) {
        try {
            ruleService.updateRule(ruleName);
        } catch (Exception e) {
            model.addAttribute("error", "Error while updating rule.");
            return "ruleName/update";
        }

        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer id, final Model model) {
        try {
            ruleService.deleteRuleById(id);
        } catch (Exception e) {
            model.addAttribute("error", "Error while deleting rule.");
            return "ruleName/list";
        }
        return "redirect:/ruleName/list";
    }
}
