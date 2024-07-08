package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.errors.UnknownCurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveController {

    private static final String CURVE_POINT_ATTRIBUTE = "curvePoint";
    private static final String MULTIPLE_CURVE_POINT_ATTRIBUTE = "curvePoints";

    private final CurvePointService curvePointService;

    @Autowired
    public CurveController(final CurvePointService curvePointService) {
        this.curvePointService = curvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(final Model model) {
        final var curvePoints = curvePointService.findAllCurvePoints();
        model.addAttribute(MULTIPLE_CURVE_POINT_ATTRIBUTE, curvePoints);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(final CurvePoint bid, final Model model) {
        model.addAttribute(CURVE_POINT_ATTRIBUTE, bid);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid final CurvePoint curvePoint, final BindingResult result, final Model model) {
        curvePointService.saveCurvePoint(curvePoint);
        model.addAttribute(CURVE_POINT_ATTRIBUTE, curvePoint);
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        final var curvePoint = curvePointService.findCurvePointById(id);

        if (curvePoint.isEmpty()) {
            model.addAttribute("error", "Unknown curve point.");
            return "curvePoint/list";
        }

        model.addAttribute(CURVE_POINT_ATTRIBUTE, curvePoint.get());
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id, @Valid final CurvePoint curvePoint,
                            final BindingResult result, final Model model) {
        try {
            curvePointService.updateCurvePoint(curvePoint);
        } catch (final UnknownCurvePoint e) {
            model.addAttribute("error", "Unknown curve point.");
            return "curvePoint/list";
        }
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id, final Model model) {
        try {
            curvePointService.deleteCurvePointById(id);
        } catch (final UnknownCurvePoint e) {
            model.addAttribute("error", "Unknown curve point.");
            return "curvePoint/list";
        }
        return "redirect:/curvePoint/list";
    }
}
