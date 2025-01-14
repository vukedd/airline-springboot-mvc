package com.project.uwd.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.uwd.models.ShoppingCart;
import com.project.uwd.models.Ticket;
import com.project.uwd.models.User;
import com.project.uwd.services.ReservationService;
import com.project.uwd.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService _reservationService;
	
	@Autowired
	private UserService _userService;
	
	@GetMapping("/")
	public String getReservationSummaryForm(HttpSession session, Model model) {
		if (session.getAttribute("loggedIn") == null) {
			return "redirect:/";
		}
		
		ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
		if (cart == null) {
			return "redirect:/";
		}
		
		model.addAttribute("tickets", cart.getItems());
		Integer points = (int)session.getAttribute("points");
		if (points != null && points > 0) {
			model.addAttribute("points", points);
			model.addAttribute("totalPrice", session.getAttribute("totalPrice"));
		} else {
			model.addAttribute("totalPrice", cart.getTotalPrice());
		}
		
		return "reservation-summary";
	}
	
	@PostMapping("/")
	public String postReservationSummaryForm(@RequestParam double totalPrice, @RequestParam int points, HttpSession session) {
		ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
		session.setAttribute("points", points);
		session.setAttribute("totalPrice", totalPrice);
		session.setAttribute("ShoppingCart", cart);
		
		return "redirect:/reservation/";
	}
	
	@PostMapping("/create")
	public String postCreateReservation(@RequestParam String[] firstName, @RequestParam String[] lastName, @RequestParam String[] passportNumber, HttpSession session) {
		ShoppingCart cart = (ShoppingCart)session.getAttribute("ShoppingCart");
		for (int i = 0; i < cart.getItems().size(); i++) {
			cart.getItems().get(i).setFirstName(firstName[i]);
			cart.getItems().get(i).setLastName(lastName[i]);
			cart.getItems().get(i).setPassportNumber(passportNumber[i]);
		}
		
//		cart.addCartItem(new Ticket (0L, 2, 1, "000000000", "Milan", "Djuric", cart.getItems().get(0).getFlight()));
		boolean reservationCreated = _reservationService.createReservation(cart.getItems(), (int)session.getAttribute("points"), (double)session.getAttribute("totalPrice"), ((User)session.getAttribute("loggedIn")).getId());
		session.removeAttribute("ShoppingCart");
		session.removeAttribute("seats");
		
		User user = (User)session.getAttribute("loggedIn");
		if (reservationCreated) {
			session.setAttribute("loggedIn", _userService.getUserById(user.getId()));
			return "redirect:/user/profile?reservation=success";
		}
		
		return "redirect:/user/profile?reservation=failure";
	}
}
