package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home(final Model model)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(final Model model)
	{
		return "redirect:/bidList/list";
	}
}
