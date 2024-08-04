package com.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.service.AuthLoginService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthLoginController {

	@Autowired
	private AuthLoginService loginService;

	@GetMapping({ "/", "/index", "/index/{authError}" })
	public String index(Model model, @PathVariable("authError") Optional<String> authError) {

		if (authError.isPresent()) {
			if ("400".equals(authError.get())) {
				model.addAttribute("errorMsg", "Invalid User Credentials");
			} else if ("401".equals(authError.get())) {
				model.addAttribute("errorMsg", "Unauthorized Access, Please login");
			} else if ("500".equals(authError.get())) {
				model.addAttribute("errorMsg", "Generic Error");
			}
		}

		return "index";
	}

	@PostMapping("/login")
	public String viewLoginPage(HttpSession session, @RequestParam(name = "username") String userName,
			@RequestParam(name = "password") String userPassword) {

		if (this.loginService.isAuthenticated(userName, userPassword)) {
			session.setAttribute("loginUser", userName);
			return "redirect:/list";
		}

		return "redirect:/index/400";
	}

	@GetMapping({ "/logout", "/logout/{authError}" })
	public String logoutUser(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("authError") Optional<String> authError) {
		HttpSession session = request.getSession(false);

		session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		for (Cookie cookie : request.getCookies()) {
			cookie.setMaxAge(0);
		}

		if (authError.isPresent()) {
			return "redirect:/index/" + authError.get();
		}

		return "redirect:/index";
	}
}
