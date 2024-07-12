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

    private static final String RULE_LIST_VIEW = "ruleName/list";
    private static final String RULE_ADD_VIEW = "ruleName/add";
    private static final String RULE_UPDATE_VIEW = "ruleName/update";

    private static final String ERROR_ATTRIBUTE = "error";

    private final RuleService ruleService;

    @Autowired
    public RuleNameController(final RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @RequestMapping("/ruleName/list")
    public String home(final Model model) {
        final var rules = ruleService.findAllRules();
        model.addAttribute(RULES_ATTRIBUTE, rules);
        return RULE_LIST_VIEW;
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(final RuleName rule, final Model model) {
        model.addAttribute(RULE_ATTRIBUTE, rule);
        return RULE_ADD_VIEW;
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid final RuleName ruleName, final BindingResult result, final Model model) {
        ruleService.saveRule(ruleName);
        model.addAttribute(RULE_ATTRIBUTE, ruleName);
        return "redirect:/" + RULE_LIST_VIEW;
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var rule = ruleService.findRuleById(id);

        if (rule.isEmpty()) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unknown rule.");
            return RULE_LIST_VIEW;
        }

        model.addAttribute(RULE_ATTRIBUTE, rule.get());
        return RULE_UPDATE_VIEW;
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer id, @Valid final RuleName ruleName,
                                 final BindingResult result, final Model model) {
        try {
            ruleService.updateRule(ruleName);
        } catch (Exception e) {
            model.addAttribute(ERROR_ATTRIBUTE, "Error while updating rule.");
            return RULE_UPDATE_VIEW;
        }
        return "redirect:/" + RULE_LIST_VIEW;
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer id, final Model model) {
        try {
            ruleService.deleteRuleById(id);
        } catch (Exception e) {
            model.addAttribute(ERROR_ATTRIBUTE, "Error while deleting rule.");
            return RULE_LIST_VIEW;
        }
        return "redirect:/" + RULE_LIST_VIEW;
    }
}
