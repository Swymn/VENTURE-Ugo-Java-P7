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

    private static final String USER_LIST_VIEW = "user/list";
    private static final String USER_ADD_VIEW = "user/add";
    private static final String USER_UPDATE_VIEW = "user/update";

    private static final String ERROR_ATTRIBUTE = "error";

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/list")
    public String home(final Model model) {
        final var users = userService.findAllUsers();
        model.addAttribute(MULTIPLE_USER_ATTRIBUTE, users);
        return USER_LIST_VIEW;
    }

    @GetMapping("/user/add")
    public String addUser(final User bid, final Model model) {
        model.addAttribute(USER_ATTRIBUTE, bid);
        return USER_ADD_VIEW;
    }

    @PostMapping("/user/validate")
    public String validate(@Valid final User user, final BindingResult result, final Model model) {
        userService.saveUser(user);
        model.addAttribute(USER_ATTRIBUTE, user);
        return "redirect:/" + USER_LIST_VIEW;
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id, final Model model) {
        final var user = userService.findUserById(id);

        if (user.isEmpty()) {
            model.addAttribute(ERROR_ATTRIBUTE, "Unknown user.");
            return USER_LIST_VIEW;
        }

        model.addAttribute(USER_ATTRIBUTE, user.get());
        return USER_UPDATE_VIEW;
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") final Integer id, @Valid final User user,
                             final BindingResult result, final Model model) {
        try {
            userService.updateUser(user);
        } catch (final Exception e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return USER_UPDATE_VIEW;
        }
        return "redirect:/" + USER_LIST_VIEW;
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") final Integer id, final Model model) {
        try {
            userService.deleteUserById(id);
        } catch (final Exception e) {
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return USER_LIST_VIEW;
        }
        return "redirect:/" + USER_LIST_VIEW;
    }
}
