package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class UserController {

    private static final String USER_ATTRIBUTE = "user";
    private static final String MULTIPLE_USER_ATTRIBUTE = "users";

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/list")
    public String home(final Model model) {
        final var users = userService.findAllUsers();
        model.addAttribute(MULTIPLE_USER_ATTRIBUTE, users);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(final User bid, final Model model) {
        model.addAttribute(USER_ATTRIBUTE, bid);
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid final User user, final BindingResult result, final Model model) {
        userService.saveUser(user);
        model.addAttribute(USER_ATTRIBUTE, user);
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var user = userService.findUserById(id);

        if (user.isEmpty()) {
            model.addAttribute("error", "Unknown user.");
            return "user/list";
        }

        model.addAttribute(USER_ATTRIBUTE, user.get());
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") final Integer id, @Valid final User user,
                             final BindingResult result, final Model model) {
        try {
            userService.updateUser(user);
        } catch (final Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/update";
        }

        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") final Integer id, final Model model) {
        try {
            userService.deleteUserById(id);
        } catch (final Exception e) {
            model.addAttribute("error", e.getMessage());
            return "user/list";
        }
        return "redirect:/user/list";
    }
}
