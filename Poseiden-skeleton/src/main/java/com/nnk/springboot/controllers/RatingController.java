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

    private static final String RATING_LIST_VIEW = "rating/list";
    private static final String RATING_ADD_VIEW = "rating/add";
    private static final String RATING_UPDATE_VIEW = "rating/update";

    private static final String ERROR_ATTRIBUTE = "error";

    private final RatingService ratingService;

    @Autowired
    public RatingController(final RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(final Model model) {
        final var rating = ratingService.findAllRating();
        model.addAttribute(MULTIPLE_RATING_ATTRIBUTE, rating);
        return RATING_LIST_VIEW;
    }

    @GetMapping("/rating/add")
    public String addRatingForm(final Rating rating, final Model model) {
        model.addAttribute(RATING_ATTRIBUTE, rating);
        return RATING_ADD_VIEW;
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid final Rating rating, final BindingResult result, final Model model) {
        ratingService.saveRating(rating);
        model.addAttribute(RATING_ATTRIBUTE, rating);
        return "redirect:/" + RATING_LIST_VIEW;
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var rating = ratingService.findRatingById(id);

        if (rating.isEmpty()) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unknown rating.");
            return RATING_LIST_VIEW;
        }

        model.addAttribute(RATING_ATTRIBUTE, rating.get());
        return RATING_UPDATE_VIEW;
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") final Integer id, @Valid final Rating rating,
                               final BindingResult result, final Model model) {

        try {
            ratingService.updateRating(rating);
        } catch (final UnknownRating e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return RATING_UPDATE_VIEW;
        }
        return "redirect:/" + RATING_LIST_VIEW;
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer id, final Model model) {
        try {
            ratingService.deleteRatingById(id);
        } catch (final UnknownRating e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return RATING_LIST_VIEW;
        }
        return "redirect:/" + RATING_LIST_VIEW;
    }
}
