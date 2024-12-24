package com.project.uwd.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.Airplane;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.services.AirplaneService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/airplane")
public class AirplaneController {
	
	@Autowired
	private AirplaneService _airplaneService;
	
	@GetMapping("/")
	public String getAllAirplanes(@RequestParam(required=false) String actionStatus, Model model) {
		List<Airplane> airplanes = _airplaneService.getAllAirplanes();
		model.addAttribute("airplanes", airplanes);
		if (actionStatus != null) {
			if (actionStatus.equals("airplaneAdded"))
				model.addAttribute("actionStatus", "airplaneAdded");
			else if (actionStatus.equals("airplaneEdited"))
				model.addAttribute("actionStatus", "airplaneEdited");
			else if (actionStatus.equals("airplaneDeleted"))
				model.addAttribute("actionStatus", "airplaneDeleted");
			else if (actionStatus.equals("airplaneDeleteError")) 
				model.addAttribute("actionStatus", "airplaneDeleteError");
			else if (actionStatus.equals("airplaneEditError")) 
				model.addAttribute("actionStatus", "airplaneEditError");
			else if (actionStatus.equals("airplaneAddError")) 
				model.addAttribute("actionStatus", "airplaneAddError");
		}
		return "airplanes";
	}
	
	@GetMapping("/details")
	public String getDetails(@RequestParam Long id, Model model) {
		Airplane airplane = _airplaneService.getAirplaneById(id);
		model.addAttribute("airplane", airplane);
		
		String currentLocation = "/airplane/details?id=" + id;
		model.addAttribute("currentLocation", currentLocation);
		model.addAttribute("idparam", "1");
		
		return "airplane-details";
	}
	
	@GetMapping("/add")
	public String getAddAirplane(@RequestParam(required=false) String name, @RequestParam(required=false) String numberOfRows, @RequestParam(required=false) String numberOfColumns, HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			if (session.getAttribute("airplane") != null) {
				model.addAttribute("airplane", session.getAttribute("airplane"));
			} else {
				model.addAttribute("airplane", new Airplane());
			}
			
			if (name != null) {
				model.addAttribute("name", name);
			}
			
			if (numberOfRows != null) {
				model.addAttribute("numberOfRows", numberOfRows);
			}
			
			if (numberOfColumns != null) {
				model.addAttribute("numberOfColumns", numberOfColumns);
			}
			
			return "airplane-add";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/add")
	public String postAddAirplane(@ModelAttribute Airplane airplane, HttpSession session) {
		StringBuilder queryParameter = new StringBuilder();
		
		if (airplane.getName() == null || airplane.getName().length() < 3) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&name=failure");
			} else {
				queryParameter.append("?name=failure");
			}
		}
		
		if (airplane.getNumberOfColumns() <= 0) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&numberOfColumns=failure");
			} else {
				queryParameter.append("?numberOfColumns=failure");
			}
		}
		
		if (airplane.getNumberOfRows() <= 0) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&numberOfRows=failure");
			} else {
				queryParameter.append("?numberOfRows=failure");
			}
		}
		
		if (queryParameter.length() > 0) {
			session.setAttribute("airplane", airplane);
			return "redirect:/airplane/add" + queryParameter.toString();
		}
		
		int res =_airplaneService.addAirplane(airplane);
		session.removeAttribute("airplane");
		if (res != 1) {
			return "redirect:/airplane/?actionStatus=airplaneAddError";
		}
		return "redirect:/airplane/?actionStatus=airplaneAdded";
	}

	@GetMapping("/edit")
	public String getEditAirplane(@RequestParam Long id, @RequestParam(required=false) String name, @RequestParam(required=false) String numberOfRows, @RequestParam(required=false) String numberOfColumns, HttpSession session,Model model) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			Airplane airplane = _airplaneService.getAirplaneById(id);
			model.addAttribute("airplane", airplane);
	
			if (name != null) {
				model.addAttribute("name", name);
			}
			
			if (numberOfRows != null) {
				model.addAttribute("numberOfRows", numberOfRows);
			}
			
			if (numberOfColumns != null) {
				model.addAttribute("numberOfColumns", numberOfColumns);
			}
			
			String currentLocation = "/airplane/edit?id=" + id;
			model.addAttribute("currentLocation", currentLocation);
			model.addAttribute("idparam", "1");
			
			return "airplane-edit";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/edit")
	public String postEditAirplane(@ModelAttribute Airplane airplane, HttpSession session) {
		StringBuilder queryParameter = new StringBuilder();
		
		if (airplane.getName() == null || airplane.getName().length() < 3) {
			queryParameter.append("&name=failure");
		}
		
		if (airplane.getNumberOfColumns() <= 0) {
			queryParameter.append("&numberOfColumns=failure");
		}
		
		if (airplane.getNumberOfRows() <= 0) {
			queryParameter.append("&numberOfRows=failure");
		}
		
		if (queryParameter.length() > 0) {
			return "redirect:/airplane/edit?id="+ airplane.getId() + queryParameter.toString();
		}
		
		int res = _airplaneService.editAirplane(airplane.getId(), airplane);
		session.removeAttribute("airplane");
		if (res != 1) {
			return "redirect:/airplane/?actionStatus=airplaneEditError";
		}
		return "redirect:/airplane/?actionStatus=airplaneEdited";
	}

	@GetMapping("/delete")
	public String postDeleteAirplane(@RequestParam Long id, HttpSession session) {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			int res = _airplaneService.deleteAirplaneById(id);
			if (res != 1) {
				return "redirect:/airplane/?actionStatus=airplaneDeleteError";
			}
			
			return "redirect:/airplane/?actionStatus=airplaneDeleted";
		}
		
		return "redirect:/";
	}
}