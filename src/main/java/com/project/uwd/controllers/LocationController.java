package com.project.uwd.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.Location;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Continent;
import com.project.uwd.models.enums.Role;
import com.project.uwd.services.LocationService;
import com.project.uwd.services.impl.LocationServiceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/location")
public class LocationController {
	
	@Autowired
	private LocationService _locationService;
	
	@PostConstruct
	private void init() {
		System.out.println("start");
	}
	
	@GetMapping("/")
	public String getLocations(@RequestParam(required=false) String actionStatus, Model model) {
		String currentLocation = "/location/";
		model.addAttribute("currentLocation", currentLocation);
		List<Location> locations = _locationService.getLocations();
		model.addAttribute("locations", locations);
		if (actionStatus != null) {
			if (actionStatus.equals("locationAdded"))
				model.addAttribute("actionStatus", "locationAdded");
			else if (actionStatus.equals("locationEdited"))
				model.addAttribute("actionStatus", "locationEdited");
			else if (actionStatus.equals("locationDeleted"))
				model.addAttribute("actionStatus", "locationDeleted");
			else if (actionStatus.equals("locationDeleteError")) 
				model.addAttribute("actionStatus", "locationDeleteError");
			else if (actionStatus.equals("locationEditError")) 
				model.addAttribute("actionStatus", "locationEditError");
			else if (actionStatus.equals("locationAddError")) 
				model.addAttribute("actionStatus", "locationAddError");
		}
//		String retval = "<!DOCTYPE html>\r\n"
//				+ "<html lang=\"en\">\r\n"
//				+ "<head>\r\n"
//				+ "    <meta charset=\"UTF-8\">\r\n"
//				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//				+ "    <title>Document</title>\r\n"
//				+ "</head>\r\n"
//				+ "<body>\r\n"
//				+ "<a href='/user/register'>Register</a><hr>";
//		if (!status.equals("")) {
//			switch (status) {
//				case "error":
//					retval += "<p style='color:red'>Error while deleting the location</p>";
//					break;
//				case "success":
//					retval += "<p style='color:darkGreen'>Location successfully deleted</p>";
//					break;
//				case "added":
//					retval += "<p style='color:darkGreen'>Location successfully added</p>";
//					break;
//			}
//		}
//		
//		for (Location location : locations) {
//			retval += "<a href='/location/details?id=" + location.getId() + "'>" + location.getCity() + "|" + location.getContinent() + "</a><br>";
//		}
//		
//		retval += "<a href='/location/add'>Add new location</a>"
//				+ "</body>\r\n"
//				+ "</html>";
//		
		return "locations";
		
	}
	
	@GetMapping("/details")
	public String getLocation(@RequestParam("id") Long id, @RequestParam(name="status", defaultValue="") String status, Model model) {
		Location location = _locationService.getLocation(id);
		model.addAttribute("location", location);
//		String retval = "<!DOCTYPE html>\r\n"
//				+ "<html lang=\"en\">\r\n"
//				+ "<head>\r\n"
//				+ "    <meta charset=\"UTF-8\">\r\n"
//				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//				+ "    <title>Document</title>\r\n"
//				+ "</head>\r\n"
//				+ "<body>\r\n";
//		
//		if (!status.equals("")) {
//			switch (status) {
//				case "error":
//					retval += "<p style='color:red'>Error while editing the location</p>";
//					break;
//				case "success":
//					retval += "<p style='color:darkGreen'>Location successfully edited</p>";
//					break;
//			}
//		}
//		
//		if (location != null) {
//			retval += "<p>" + location.getCity() + "|" + location.getContinent() + "</p>"
//					+ "<a href='/location/delete?id=" + location.getId() + "'>Delete</a><br>"
//					+ "<a href='/location/edit?id=" + location.getId() + "'>Edit</a><br>"
//					+ "<a href='/location/'>Show all locations</a>";
//		} else {
//			retval += "<p>Location not found<p>";
//		}
//		
//		retval += "</body>\r\n"
//				+ "</html>";
		String currentLocation = "/location/details?id=" + id;
		model.addAttribute("currentElement", currentLocation);
		model.addAttribute("idparam", "1");
		return "location-details";
	}
	
	@GetMapping("/delete")
	public void deleteLocation(@RequestParam("id") Long id, HttpServletResponse response, HttpSession session) throws IOException {
		User user = (User) session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			int res = _locationService.deleteLocation(id);
			System.out.println(res);
			if (res == 1) {
				response.sendRedirect("/location/?actionStatus=locationDeleted");
			} else {
				response.sendRedirect("/location/?actionStatus=locationDeleteError");
			}
			return;
		}
	
		response.sendRedirect("/");
	}
	
	@GetMapping("/add")
	public String getAddLocation(@RequestParam(required=false) String city, @RequestParam(required=false) String country, @RequestParam(required=false) String image, HttpSession session, Model model) {
		User user = (User) session.getAttribute("loggedIn");
		if (user == null || user.getRole().compareTo(Role.Admin) != 0) {
			return "redirect:/";
		}
		Continent[] continents = Continent.values();
		model.addAttribute("continents", continents);
		
		if (session.getAttribute("location") != null) {
			model.addAttribute("location", session.getAttribute("location"));
		} else {
			model.addAttribute("location", new Location());
		}
		
		if (city != null) {
			model.addAttribute("city", "failure");
		}
		
		if (country != null) {
			model.addAttribute("country", "failure");
		}
		
		if (image != null) {
			model.addAttribute("image", "failure");
		}
//		String retval = "<!DOCTYPE html>\r\n"
//				+ "<html lang=\"en\">\r\n"
//				+ "<head>\r\n"
//				+ "    <meta charset=\"UTF-8\">\r\n"
//				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//				+ "    <title>Document</title>\r\n"
//				+ "</head>\r\n"
//				+ "<body>\r\n"
//				+ "    <h1>Add new location</h1>\r\n";
//		Location location = null;
//		boolean invalidTry = false;
//		if (session.getAttribute("location") != null) {
//			location = (Location)session.getAttribute("location");
//			invalidTry = true;
//		}
//		
//		retval += "    <form method='Post' action='/location/add'>\r\n"
//// city
//				+ "        <label>City:</label><br>\r\n";
//		if (invalidTry) {
//			retval += "        <input type=\"text\" name=\"city\" value='" + location.getCity() + "'><br>\r\n";
//		} else { 
//			retval += "        <input type=\"text\" name=\"city\"><br>\r\n";
//		}
//		if (city != null && city.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>City must contain at least 3 characters</p>";
//		}
//// country
//		retval += "        <label>Country:</label><br>\r\n";
//			
//		if (invalidTry) {
//			retval += "        <input type=\"text\" name=\"country\" value='" + location.getCountry() + "'><br>\r\n";
//		} else { 
//			retval += "        <input type=\"text\" name=\"country\"><br>\r\n";
//		}
//		if (country != null && country.equals("failure")) {
//					retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>Country must contain at least 3 characters</p>";
//				}
//// continent
//		retval += "        <label>Continent:</label><br>\r\n"
//				+ "        <select name=\"continent\">\r\n";
//		
//		for (Continent continent : continents) {
//			if (location != null && location.getContinent().equals(continent)) {
//				retval += "            <option selected value='" + continent + "'>" + continent.name() + "</option>\r\n";
//			}
//			retval += "            <option value='" + continent + "'>" + continent.name() + "</option>\r\n";
//		}
//		retval += "        </select><br>\r\n"
//				+ "   	   <button type='submit'>Add location</button>"
//				+ "   </form>\r\n"
//				+ "</body>\r\n"
//				+ "</html>";
		return "location-add";
	}
	
	@PostMapping("/add")
	public void postAddLocation(@ModelAttribute Location location, @RequestParam(required=false) String city, @RequestParam(required=false) String country, @RequestParam(required=false) MultipartFile locationImage ,HttpServletResponse response,BindingResult result, HttpSession session) throws IOException {		
		StringBuilder queryParameter = new StringBuilder();

		if (location.getCity().length() < 3) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&city=failure");
			} else {
				queryParameter.append("?city=failure");
			}
		}

		if (location.getCountry().length() < 3) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&country=failure");
			} else {
				queryParameter.append("?country=failure");
			}
		}

		if (location.getContinent() == null) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&continent=failure");
			} else {
				queryParameter.append("?continent=failure");
			}
		}
		
		if (locationImage.isEmpty()) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&image=failure");
			} else {
				queryParameter.append("?image=failure");
			}
		}
		
		if (queryParameter.length() > 0) {
			response.sendRedirect("/location/add" + queryParameter.toString());
			session.setAttribute("location", location);
			return;
		}
		
		int res =_locationService.addLocation(location, locationImage);
		if (res == 1) {
			session.removeAttribute("location");		
			response.sendRedirect("/location/?actionStatus=locationAdded");
			return;
		}
		
		session.removeAttribute("location");		
		response.sendRedirect("/location/?actionStatus=locationAddError");
		return;
	}
	
	@GetMapping("/edit")
	public String getEditLocation(@RequestParam(required=false) String city, @RequestParam(required=false) String country ,@RequestParam(name="status", defaultValue="") String status, @RequestParam(name="id") Long id, HttpServletResponse response, HttpSession session, Model model) throws IOException {
		User user = (User)session.getAttribute("loggedIn");
		if (user != null && user.getRole().compareTo(Role.Admin) == 0) {
			
			Location location = _locationService.getLocation(id);
	//		if (location == null) {
	//			response.sendRedirect("/location/");
	//			return;
	//		}
	//		
	//		Location locationSession = null;
	//		boolean invalidTry = false;	
	//		if (session.getAttribute("location") != null) {
	//			locationSession = (Location)session.getAttribute("location");
	//			invalidTry = true;
	//		}
	//		
	//		PrintWriter out = response.getWriter();
			Continent[] continents = Continent.values();
	//		out.write("<!DOCTYPE html>\r\n"
	//				+ "<html lang=\"en\">\r\n"
	//				+ "<head>\r\n"
	//				+ "    <meta charset=\"UTF-8\">\r\n"
	//				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
	//				+ "    <title>Document</title>\r\n"
	//				+ "</head>\r\n"
	//				+ "<body>\r\n"
	//				+ "    <h1>Edit location</h1>\r\n");
	//		
	//		if (status.equals("error")) {
	//			out.write("<p style='color:red'>Error while editing location</p>");
	//		}
	//		
	//		out.write("    <form method='Post' action='/location/edit?id=" + location.getId() + "'>\r\n"
	//				+ "        <label>City:</label><br>\r\n");
	//		if (invalidTry) {
	//			out.write("        <input type=\"text\" name=\"city\" value='" + locationSession.getCity() + "'><br>\r\n");
	//		} else { 
	//			out.write("        <input type=\"text\" name=\"city\" value='" + location.getCity() + "'><br>\r\n");
	//		}
	//		if (city != null && city.equals("failure")) {
	//			out.write("<p style='color:red;font-size:12px;margin:0;pading:0;'>City must contain at least 3 characters</p>");
	//		}
	//		out.write("        <label>Country:</label><br>\r\n");
	//		if (invalidTry) {
	//			out.write("        <input type=\"text\" name=\"country\" value='" + locationSession.getCountry() + "'><br>\r\n");
	//		} else { 
	//			out.write("        <input type=\"text\" name=\"country\" value='" + location.getCountry() + "'><br>\r\n");
	//		}
	//		if (country != null && country.equals("failure")) {
	//			out.write("<p style='color:red;font-size:12px;margin:0;pading:0;'>Country must contain at least 3 characters</p>");
	//		}
	//		out.write("        <label>Continent:</label><br>\r\n"
	//				+ "        <select name=\"continent\">\r\n");
	//		
	//		for (Continent continent : continents) {
	//			if (locationSession != null && locationSession.getContinent().equals(continent)) {
	//				out.write("            <option selected value='" + continent + "'>" + continent.name() + "</option>\r\n");
	//			} else if (continent.equals(location.getContinent())) {
	//				out.write("            <option selected value='" + continent + "'>" + continent + "</option>\r\n");
	//			}
	//			else {
	//				out.write("            <option value='" + continent + "'>" + continent + "</option>\r\n");
	//			}
	//		}
	//		out.write("        </select><br>\r\n"
	//				+ "   	   <button type='submit'>Save changes</button>"
	//				+ "   </form>\r\n"
	//				+ "<a href='/location/details?id=" + location.getId() + "'>Go back</a>"
	//				+ "</body>\r\n"
	//				+ "</html>");
	//		
			model.addAttribute("location", location);
			model.addAttribute("continents", continents);
			
			if (id != null) {
				model.addAttribute("locationId", id);
			}
			
			if (city != null) {
				model.addAttribute("city", "failure");
			}
			
			if (country != null) {
				model.addAttribute("country", "failure");
			}
			
			model.addAttribute("idparam", 1);
			model.addAttribute("currentElement", "/location/edit?id=" + id);
			
			return "location-edit";
		}
		
		return "redirect:/";
	}
	
	@PostMapping("/edit")
	public void postEditLocation(@RequestParam Long id, @ModelAttribute Location location, HttpServletResponse response, HttpSession session, BindingResult result) throws IOException {
		if (result.hasErrors()) {
			response.sendRedirect("/location/edit?id=" + id);
		}
		
		StringBuilder queryParameter = new StringBuilder();

		if (location.getCity().length() < 3) {
			queryParameter.append("&city=failure");
		}

		if (location.getCountry().length() < 3) {
			queryParameter.append("&country=failure");
		}

		if (location.getContinent() == null) {
			queryParameter.append("&continent=failure");
		}
		
		if (queryParameter.length() > 0) {
			session.setAttribute("location", location);
			response.sendRedirect("/location/edit?id=" + id + queryParameter.toString());
			return;
		}
		
		int res = _locationService.editLocation(id, location);
		if (res != 1) {
			response.sendRedirect("/location/?actionStatus=locationEditError");
			return;
		}
		
		response.sendRedirect("/location/?actionStatus=locationEdited");
		return;
	}
}
