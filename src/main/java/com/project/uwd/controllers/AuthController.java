package com.project.uwd.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.User;
import com.project.uwd.services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	AuthService _authService;
	
	@GetMapping("/login")
	public String getLogIn(@RequestParam(required=false)String register, @RequestParam(required=false)String login, Model model) {
		if (register != null && register.equals("success")) {
			model.addAttribute("register", register);
		}
		
		if (login != null && login.equals("fail")) {
			model.addAttribute("login", login);
		}
		
		return "log-in";
	}
	
	@PostMapping("/login")
	public String postLogIn(@RequestParam String username, @RequestParam String password, HttpSession session) {
		User user = _authService.authenticateUser(username, password);
		if (user != null) {
			session.setAttribute("loggedIn", user);
		} else {
			return "redirect:/auth/login?login=fail";
		}
		
		if (session.getAttribute("searchForceRedirect") != null && (boolean)session.getAttribute("searchForceRedirect") == true) {
			return "redirect:/";
		}
		
		if (session.getAttribute("forceAuthenticationFlightBooking") != null && (boolean)session.getAttribute("forceAuthenticationFlightBooking") == true) {
			return "redirect:/flight/details?id=" + session.getAttribute("flightId");
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String postLogOut(HttpSession session) {
		session.removeAttribute("loggedIn");
		session.invalidate();
		return "redirect:/auth/login";
	}
}
