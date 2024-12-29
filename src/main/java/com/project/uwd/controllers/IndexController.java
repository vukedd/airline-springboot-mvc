package com.project.uwd.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.services.FlightService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private FlightService _flightService;
	
	@GetMapping
	public String getIndexPage(Model model) {
		model.addAttribute("flights", _flightService.getAllFlights());
		
		return "index";
	}
	
	@PostMapping("search")
	public String searchFlights(@RequestParam(required=false) String departure, @RequestParam(required=false) String destination, @RequestParam(required=false) LocalDate dateOfDeparture, @RequestParam(required=false, defaultValue="0") int numberOfSeats, Model model) {
		model.addAttribute("flights", _flightService.searchFlight(departure, destination, dateOfDeparture, numberOfSeats));
		return "index";
	}
}
