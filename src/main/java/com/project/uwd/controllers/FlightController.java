package com.project.uwd.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.uwd.models.Flight;
import com.project.uwd.models.ShoppingCart;
import com.project.uwd.models.Ticket;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.services.AirplaneService;
import com.project.uwd.services.AirportService;
import com.project.uwd.services.FlightService;

import ch.qos.logback.core.model.conditional.IfModel;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/flight")
public class FlightController {
	
	@Autowired
	private FlightService _flightService;
	
	@Autowired
	private AirportService _airportService;
	
	@Autowired
	private AirplaneService _airplaneService;
	
	@GetMapping("/")
	public String getFlights(@RequestParam(required=false) String actionStatus, Model model) {
		if (actionStatus != null) {
			if (actionStatus.equals("flightAdded")) {
				model.addAttribute("actionStatus", actionStatus);
			} else if (actionStatus.equals("flightDeleted")) {
				model.addAttribute("actionStatus", actionStatus);
			}
		}
		
		model.addAttribute("flights", _flightService.getAllFlights());
		return "flights";
	}
	
	@GetMapping("/details")
	public String getFlightDetails(@RequestParam Long id, @RequestParam(required=false) String booked, @RequestParam(required=false) String actionStatus,Model model, HttpSession session) {
		if (booked != null) {
			model.addAttribute("booked", booked);
		}
		
		if (actionStatus != null) {
			if (actionStatus.equals("deleteError")) {
				model.addAttribute("actionStatus", actionStatus);
			} else if (actionStatus.equals("addToCartError")) {
				model.addAttribute("actionStatus", actionStatus);
			}
		}
		model.addAttribute("idparam", 1);
		model.addAttribute("currentElement", "/flight/details?id=" + id);
		model.addAttribute("flight", _flightService.getFlightById(id));
		
		if (session.getAttribute("seats") != null) {
			if (!((HashMap<Long,int[][]>)session.getAttribute("seats")).containsKey(id)) {
				model.addAttribute("seats", _flightService.getTakenSeatsByFlightId(id));
			} else {
				model.addAttribute("seats", ((HashMap<Long, int[][]>)session.getAttribute("seats")).get(id));
			}
		}
		else {
			model.addAttribute("seats", _flightService.getTakenSeatsByFlightId(id));
		}
		
		return "flight-details";
	}
	
	@GetMapping("/add")
	public String getAddFlightForm(@RequestParam(required=false) String depDest, @RequestParam(required=false) String departureDate, @RequestParam(required=false) String flightDuration, @RequestParam(required=false) String ticketPrice, HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			model.addAttribute("airports", _airportService.getAllAiports());
			model.addAttribute("airplanes", _airplaneService.getAvailableAirplanes());
			
			if (depDest != null) {
				model.addAttribute("depDest", depDest);
			}
			
			if (departureDate != null) {
				model.addAttribute("departureDate", departureDate);
			}
			
			if (flightDuration != null) {
				model.addAttribute("flightDuration", flightDuration);
			}
			
			if (ticketPrice != null) {
				model.addAttribute("ticketPrice", ticketPrice);
			}
			
			if (session.getAttribute("flight") == null)
				model.addAttribute("flight", new Flight());
			else
				model.addAttribute("flight", session.getAttribute("flight"));
			
			
			
			return "flight-add";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/add")
	public String postAddFlight(@ModelAttribute Flight flight, HttpSession session) {
		StringBuilder queryParameters = new StringBuilder();
		
		if (flight.getDepartureId() == flight.getDestinationId()) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&depDest=failure");
			} else {
				queryParameters.append("?depDest=failure");
			}
		}
		
		if (flight.getDateOfDeparture().isBefore(LocalDate.now()) || flight.getDateOfDeparture() == LocalDate.now()) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&departureDate=failure");
			} else {
				queryParameters.append("?departureDate=failure");
			}
		}
		
		if (flight.getDuration() <= 0) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&flightDuration=failure");
			} else {
				queryParameters.append("?flightDuration=failure");
			}
		}
		
		if (flight.getTicketPrice() <= 0) {
			if (queryParameters.length() > 0) {
				queryParameters.append("&ticketPrice=failure");
			} else {
				queryParameters.append("?ticketPrice=failure");
			}
		}
		
		if (queryParameters.length() > 0) {
			session.setAttribute("flight", flight);
			return "redirect:/flight/add" + queryParameters.toString();
		}
		
		int res = _flightService.createFlight(flight);
		session.removeAttribute("flight");
		if (res == 0) {
			return "redirect:/flight/?actionStatus=flightAddError";
		}
		
		return "redirect:/flight/?actionStatus=flightAdded";
	}
	
	@GetMapping("/cancel")
	public String cancelFlight(@RequestParam Long id) {
		int res = _flightService.cancelFlight(id);
		if (res == 1) {
			return "redirect:/flight/?cancelled=true";
		}
		
		return "redirect:/flight/?cancelled=false";
	}
	
	@PostMapping("/book")
	public String bookFlight(@RequestParam Long flightId, @RequestParam String[] selectedSeats, HttpSession session) {
		if (session.getAttribute("loggedIn") != null) {
			Long cartIndex = 1l;
			if (session.getAttribute("cartIndex") != null) {
				cartIndex = (Long)session.getAttribute("cartIndex");
			}
			
			ShoppingCart shoppingCart = null;
			if (session.getAttribute("ShoppingCart") == null) {
				shoppingCart = new ShoppingCart();
			} else {
				shoppingCart = (ShoppingCart)session.getAttribute("ShoppingCart");
			}
			
			
			Map<Long,int[][]> seats = null;
			if (session.getAttribute("seats") == null) {
				seats = new HashMap<Long, int[][]>();
			} else {
				seats = (Map<Long, int[][]>)session.getAttribute("seats");
			}
			
			if (!seats.containsKey(flightId)) 
				seats.put(flightId, _flightService.getTakenSeatsByFlightId(flightId));
	
			for (String seat : selectedSeats) {
				String[] seatNumberParse = seat.split("-");
				Ticket ticket = new Ticket();
				ticket.setId(cartIndex++);
				ticket.setFlightId(flightId);
				ticket.setColumnNumber(Integer.parseInt(seatNumberParse[0]));
				ticket.setRowNumber(Integer.parseInt(seatNumberParse[1]));
				ticket.setFlight(_flightService.getFlightById(flightId));
				shoppingCart.addCartItem(ticket);
				seats.get(flightId)[ticket.getColumnNumber() - 1][ticket.getRowNumber() - 1] = 1;
			}
			
			session.setAttribute("ShoppingCart", shoppingCart);
			session.setAttribute("cartIndex", cartIndex);
			session.setAttribute("cartSize", shoppingCart.getTotalNumberOfItems());
			session.setAttribute("seats", seats);
			
			return "redirect:/cart";
		}
		
		session.setAttribute("forceAuthenticationFlightBooking", true);
		session.setAttribute("flightId", flightId);
		
		return "redirect:/auth/login";
//		boolean numberOfTicketsValidation = true;
//		if (session.getAttribute("loggedIn") == null) {
//			session.setAttribute("flightId", flightId);
//			session.setAttribute("forceAuthenticationFlightBooking", true);
//			session.setAttribute("numberOfTickets", numberOfTickets);
//			
//			return "redirect:/auth/login";
//		}
//		
//		if (numberOfTickets < 0 || numberOfTickets > 11) {
//			numberOfTicketsValidation = false;
//		}
//		
//		int ticketCount = 0;
//
//		Object test = session.getAttribute("FlightTicketTracker");
//		if (session.getAttribute("FlightTicketTracker") != null && ((Map<Long, Integer>)session.getAttribute("FlightTicketTracker")).containsKey(flightId)) {
//			ticketCount = ((int)((Map<Long, Integer>)session.getAttribute("FlightTicketTracker")).get(flightId)) + numberOfTickets;
//		} else
//			ticketCount = 0;
//		
//		if (numberOfTicketsValidation && _flightService.numberOfAvailableSpotsByFlight(flightId) -  ticketCount >= 0) {
//			ShoppingCart cart = new ShoppingCart();
//			if (session.getAttribute("ShoppingCart") == null)
//				cart = new ShoppingCart();
//			else
//				cart = (ShoppingCart)session.getAttribute("ShoppingCart");
//			
//			cart.addCartItem(_flightService.getFlightById(flightId), numberOfTickets);
//			
//			session.setAttribute("cartSize", cart.getTotalNumberOfItems());
//			session.setAttribute("ShoppingCart", cart);
//			
//			Map<Long, Integer> flightTicketTracker;
//			
//			if (session.getAttribute("FlightTicketTracker") == null) {
//				flightTicketTracker = new HashMap<Long, Integer>();
//				flightTicketTracker.put(flightId, numberOfTickets);
//				
//				session.setAttribute("FlightTicketTracker", flightTicketTracker);
//			} else {
//				flightTicketTracker = (Map<Long, Integer>)session.getAttribute("FlightTicketTracker");
//				
//				if (flightTicketTracker.containsKey(flightId)) {
//					flightTicketTracker.put(flightId, flightTicketTracker.get(flightId) + numberOfTickets);
//				} else {
//					flightTicketTracker.put(flightId, numberOfTickets);
//				}
//			}
//			
//			return "redirect:/cart";
//		}
//		
//		session.setAttribute("numberOfTickets", numberOfTickets);
//		session.setAttribute("flightId", flightId);
//
//		return "redirect:/flight/details?id=" + flightId + "&booked=false";
	}
	
	@GetMapping("/delete")
	public String deleteFlight(@RequestParam Long id, HttpSession session) {
		if (session.getAttribute("loggedIn") != null && ((User)session.getAttribute("loggedIn")).getRole().equals(Role.Admin)) {
			int rowsAffected = _flightService.deleteFlight(id);
			if (rowsAffected == 0) {
				return "redirect:/flight/details?id=" + id + "&actionStatus=deleteError";
			} else if (rowsAffected == 1) {
				return "redirect:/flight/?actionStatus=flightDeleted";
			}
		}
		
		return "redirect:/";
	}

//	boolean timeDifference = LocalDateTime.of(date, time).isAfter(LocalDateTime.now());
//	if (timeDifference) {
//		int flightTime = 0;
//		int currentTime = 0;
//		
//		flightTime += (60 * time.getHour()) + time.getMinute();
//		System.out.println(flightTime);
//		currentTime += (60 * LocalTime.now().getHour()) + LocalTime.now().getMinute();
//		System.out.println(currentTime);
//
//		if (flightTime - currentTime > 60) {
//			System.out.println("Let je otkazan!");
//		} else {
//			System.out.println("Let je nemoguce otkazati!");
//		}
//	}
}
