package com.zettamine.materialInspection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zettamine.materialInspection.entities.User;
import com.zettamine.materialInspection.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/home")
	public String loginAuthentication(User user, Model model,HttpServletRequest request) {
		User existedUser = userService.getByUserNameAndPassword(user.getUserName().toUpperCase(), user.getPassword());
		if (existedUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute("user", existedUser);
			return "home";
		}
		model.addAttribute("invalid", "Invalid Credentials");
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/"; 
	}

}
