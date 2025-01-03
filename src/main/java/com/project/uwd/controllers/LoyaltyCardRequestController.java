package com.project.uwd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.LoyaltyCardRequest;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.services.LoyaltyCardRequestService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/loyaltyCardRequest")
public class LoyaltyCardRequestController {

	@Autowired
	private LoyaltyCardRequestService _loyaltyCardRequestService;
	
	@RequestMapping("/submit")
	public String submitLoyaltyCardRequest(HttpSession session) {
		User requestedBy = (User)session.getAttribute("loggedIn");
		if (requestedBy != null) {
			boolean requestedSuccessfully = _loyaltyCardRequestService.createLoyaltyCardRequest(requestedBy.getId());
			
			if (!requestedSuccessfully)
				return "redirect:/user/profile?loyaltyCardRequest=error";
				
			return "redirect:/user/profile?loyaltyCardRequest=success";
		}
		
		return "redirect:/auth/login";
	}
	
	@RequestMapping("/")
	public String getLoyaltyCardRequests(HttpSession session, Model model) {
		if (session.getAttribute("loggedIn") != null) {
			User loggedIn = (User)session.getAttribute("loggedIn");
			if (!loggedIn.getRole().equals(Role.Admin)) {
				return "redirect:/auth/login";
			}
			
			model.addAttribute("loyaltyCardRequests", _loyaltyCardRequestService.getLoyaltyCardRequests());
			return "loyalty-card-requests";
		}
				
		return "redirect:/auth/login";
	}
	
	@RequestMapping("/approve")
	public String approveLoyaltyCardRequest(@RequestParam Long id, HttpSession session) {
		if (session.getAttribute("loggedIn") != null) {
			User loggedIn = (User)session.getAttribute("loggedIn");
			if (!loggedIn.getRole().equals(Role.Admin)) {
				return "redirect:/auth/login";
			}
			
			LoyaltyCardRequest request =  _loyaltyCardRequestService.getLoyaltyCardRequestById(id);
			
			if (!_loyaltyCardRequestService.approveLoyaltyCardRequest(id, request.getRequestedById())) {
				return "redirect:/loyaltyCardRequest/?approve=failure";
			}
			return "redirect:/loyaltyCardRequest/?approve=success";
		}
		
		return "redirect:/auth/login";
	}
	
	@RequestMapping("/deny")
	public String denyLoyaltyCardRequest(@RequestParam Long id, HttpSession session) {
		if (session.getAttribute("loggedIn") != null) {
			User loggedIn = (User)session.getAttribute("loggedIn");
			if (!loggedIn.getRole().equals(Role.Admin)) {
				return "redirect:/auth/login";
			}
			LoyaltyCardRequest request =  _loyaltyCardRequestService.getLoyaltyCardRequestById(id);
			
			if (!_loyaltyCardRequestService.denyLoyaltyCardRequest(id)) {
				return "redirect:/loyaltyCardRequest/?deny=failure";
			}
			return "redirect:/loyaltyCardRequest/?deny=success";
		}
		
		return "redirect:/auth/login";
	}
};
