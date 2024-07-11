package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {

    private static final String TRADE_ATTRIBUTE = "trade";
    private static final String MULTIPLE_TRADE_ATTRIBUTE = "trades";

    private final TradeService tradeService;

    @Autowired
    public TradeController(final TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(final Model model) {
        final var trade = tradeService.findAllTrades();
        model.addAttribute(MULTIPLE_TRADE_ATTRIBUTE, trade);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(final Trade bid, final Model model) {
        model.addAttribute(TRADE_ATTRIBUTE, bid);
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid final Trade trade, final BindingResult result, final Model model) {
        tradeService.saveTrade(trade);
        model.addAttribute(TRADE_ATTRIBUTE, trade);
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var trade = tradeService.findTradeById(id);

        if (trade.isEmpty()) {
            model.addAttribute("error", "Unknown trade.");
            return "trade/list";
        }

        model.addAttribute(TRADE_ATTRIBUTE, trade.get());
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") final Integer id, @Valid final Trade trade,
                             final BindingResult result, final Model model) {
        try {
            tradeService.updateTrade(trade);
        } catch (final Exception e) {
            model.addAttribute("error", e.getMessage());
            return "trade/update";
        }

        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") final Integer id, final Model model) {
        try {
            tradeService.deleteTrade(id);
        } catch (final Exception e) {
            model.addAttribute("error", e.getMessage());
            return "trade/list";
        }
        return "redirect:/trade/list";
    }
}
