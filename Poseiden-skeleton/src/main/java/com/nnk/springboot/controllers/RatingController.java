package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.errors.UnknownRating;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {

    private static final String RATING_ATTRIBUTE = "rating";
    private static final String MULTIPLE_RATING_ATTRIBUTE = "ratings";

    private final RatingService ratingService;

    @Autowired
    public RatingController(final RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(final Model model) {
        final var rating = ratingService.findAllRating();
        model.addAttribute(MULTIPLE_RATING_ATTRIBUTE, rating);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(final Rating rating, final Model model) {
        model.addAttribute(RATING_ATTRIBUTE, rating);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid final Rating rating, final BindingResult result, final Model model) {
        ratingService.saveRating(rating);
        model.addAttribute(RATING_ATTRIBUTE, rating);
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var rating = ratingService.findRatingById(id);

        if (rating.isEmpty()) {
            model.addAttribute("error", "Unknown rating.");
            return "rating/list";
        }

        model.addAttribute(RATING_ATTRIBUTE, rating.get());
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") final Integer id, @Valid final Rating rating,
                               final BindingResult result, final Model model) {

        try {
            ratingService.updateRating(rating);
        } catch (final UnknownRating e) {
            model.addAttribute("error", e.getMessage());
            return "rating/list";
        }

        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer id, final Model model) {
        try {
            ratingService.deleteRatingById(id);
        } catch (final UnknownRating e) {
            model.addAttribute("error", e.getMessage());
            return "rating/list";
        }
        return "redirect:/rating/list";
    }
}
