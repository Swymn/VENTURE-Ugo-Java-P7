package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.errors.UnknownBidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {

    private static final String BID_LIST_ATTRIBUTE = "bidList";
    private static final String MULTIPLE_BID_LIST_ATTRIBUTE = "bidLists";

    private static final String BID_LIST_LIST_VIEW = "bidList/list";
    private static final String BID_LIST_ADD_VIEW = "bidList/add";
    private static final String BID_LIST_UPDATE_VIEW = "bidList/update";

    private static final String ERROR_ATTRIBUTE = "error";

    private final BidListService bidService;

    @Autowired
    public BidListController(final BidListService bidService) {
        this.bidService = bidService;
    }

    @RequestMapping("/bidList/list")
    public String home(final Model model) {
        final var bidLists = bidService.findAllBidList();
        model.addAttribute(MULTIPLE_BID_LIST_ATTRIBUTE, bidLists);
        return BID_LIST_LIST_VIEW;
    }

    @GetMapping("/bidList/add")
    public String addBidForm(final BidList bid, final Model model) {
        model.addAttribute(BID_LIST_ATTRIBUTE, bid);
        return BID_LIST_ADD_VIEW;
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid final BidList bid, final BindingResult result, final Model model) {
        bidService.saveBidList(bid);
        model.addAttribute(BID_LIST_ATTRIBUTE, bid);
        return "redirect:/" + BID_LIST_LIST_VIEW;
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var bid = bidService.findBidListById(id);

        if (bid.isEmpty()) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unknown bid list.");
            return BID_LIST_LIST_VIEW;
        }

        model.addAttribute(BID_LIST_ATTRIBUTE, bid.get());
        return BID_LIST_UPDATE_VIEW;
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id, @Valid final BidList bidList,
                            final BindingResult result, final Model model) {
        try {
            bidService.updateBidList(bidList);
        } catch (final UnknownBidList e) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unable to delete the bid list.");
            return BID_LIST_UPDATE_VIEW;
        }
        return "redirect:/" + BID_LIST_LIST_VIEW;
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id, final Model model) {
        try {
            bidService.deleteBidList(id);
        } catch (final UnknownBidList e) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unable to delete the bid list.");
            return BID_LIST_LIST_VIEW;
        }
        return "redirect:/" + BID_LIST_LIST_VIEW;
    }
}
