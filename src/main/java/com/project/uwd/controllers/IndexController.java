package com.project.uwd.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.Flight;
import com.project.uwd.services.FlightService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private FlightService _flightService;
	
	@GetMapping
	public String getIndexPage(Model model) {
		model.addAttribute("flights", _flightService.getFlightsOnDiscount());
		
		return "index";
	}
	
	@PostMapping("search")
	public String searchFlights(@RequestParam(required=false) String departure, @RequestParam(required=false) String destination, @RequestParam(required=false) LocalDate dateOfDeparture, @RequestParam(required=false, defaultValue="0") int numberOfSeats, @RequestParam(required=false) boolean similarFlights, Model model, HttpSession session) {
		if (session.getAttribute("loggedIn") == null) {
			session.setAttribute("searchForceRedirect", true);
			return "redirect:/auth/login";
		}
		
		List<Flight> flights = _flightService.searchFlight(departure, destination, dateOfDeparture, numberOfSeats, similarFlights);
		if (flights.size() > 0) {
			model.addAttribute("flights", flights);
		} else {
			List<Flight[]> connectedFlights = _flightService.getConnectedFlights(departure, destination, dateOfDeparture, numberOfSeats);
			model.addAttribute("flights", null);
		}
		
		return "index";
	}
	
	
}
