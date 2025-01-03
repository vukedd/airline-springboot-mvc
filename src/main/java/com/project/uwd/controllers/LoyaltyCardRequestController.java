package com.project.uwd.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.uwd.models.User;
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
		boolean requestedSuccessfully = _loyaltyCardRequestService.createLoyaltyCardRequest(requestedBy.getId());
		
		if (!requestedSuccessfully)
			return "redirect:/user/profile?loyaltyCardRequest=error";
			
		return "redirect:/user/profile?loyaltyCardRequest=success";
	}
}
