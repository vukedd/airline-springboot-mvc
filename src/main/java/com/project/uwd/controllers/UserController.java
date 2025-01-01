	package com.project.uwd.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.User;
import com.project.uwd.services.ReservationService;
import com.project.uwd.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService _userService;	
	
	@Autowired
	ReservationService _reservationService;
	
	@GetMapping("/")
	@ResponseBody
	public String getUsers() throws URISyntaxException, IOException {
		List<User> users = CSVResourceProvider.getInstance().getAllUsers();
		for (User u : users) {
			System.out.println(u);
		}
		return "Hello";
	}

	@GetMapping("/register")
	public String getRegister(@RequestParam(required = false) String username,
			@RequestParam(required = false) String email, @RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName, @RequestParam(required = false) String password,
			@RequestParam(required = false) String dateOfBirth, @RequestParam(required = false) String status, Model model, HttpSession session) {
		boolean invalidTry = false;
		
		model.addAttribute("user", new User());
		if (email != null) {
			model.addAttribute("email", "failure");
		}
		
		if (username != null) {
			if (username.equals("exists")) {
				model.addAttribute("username", "exists");
			} else {
				model.addAttribute("username", "failure");
			}
		}
		
		if (firstName != null) {
			model.addAttribute("firstName", "failure");
		}
		
		if (lastName != null) {
			model.addAttribute("lastName", "failure");
		}
		
		if (password != null) {
			model.addAttribute("password", "failure");
		}
		
		if (dateOfBirth != null) {
			model.addAttribute("dateOfBirth", "failure");
		}
		
		if (status != null) {
			model.addAttribute("status", "success");
		}
//		if (session.getAttribute("registerFormData") != null) {
//			invalidTry = true;
//			user = (User)session.getAttribute("registerFormData");
//		}
//		
//		String retval = "<!DOCTYPE html>\r\n" + "<html lang=\"en\">\r\n" + "<head>\r\n"
//				+ "    <meta charset=\"UTF-8\">\r\n"
//				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
//				+ "    <title>Document</title>\r\n" + "</head>\r\n" + "<body>\r\n"
//				+ "<a href='/location/'>Show all locations</a>"
//				+ "    <h2>Register</h2>\r\n";
//		if (status != null && status.equals("success")) {
//			retval += "<p style='color:green'>User successfully registered!</p>";
//		}
//		// first name
//		retval += "    <form method=\"POST\" action=\"/user/register\">\r\n" +
//				  "        <label>First Name:</label><br>\r\n";
//		
//		if (invalidTry) {
//			retval += "        <input type=\"text\" name=\"firstName\" value='" + user.getFirstName() + "'><br>\r\n";
//		} else {
//			retval += "        <input type=\"text\" name=\"firstName\"><br>\r\n";
//		}
//		if (firstName != null && firstName.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>First name must contain only letters</p>";
//		}
//		// last name
//		retval += "        <label>Last Name:</label><br>\r\n";
//		if (invalidTry) {
//			retval += "        <input type=\"text\" name=\"lastName\" value='" + user.getLastName() + "'><br>\r\n";
//		} else {
//			retval += "        <input type=\"text\" name=\"lastName\"><br>\r\n";
//		}	
//		if (lastName != null && lastName.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>Last name must contain only letters</p>";
//		}
//		
//		// DOB
//		retval += "        <label>Date of birth:</label><br>\r\n";
//		if (invalidTry) {
//			retval += 		"        <input type=\"date\" name=\"dateOfBirth\" value='" + user.getDateOfBirth() + "' reqiured><br>\r\n";
//		} else {
//			retval += 		"        <input type=\"date\" name=\"dateOfBirth\" required><br>\r\n";
//		}	
//		if (dateOfBirth != null && dateOfBirth.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>Please select date of birth</p>";
//		}
//		
//		// Username
//		retval += "        <label>Username:</label><br>\r\n";
//		if (invalidTry) {
//			retval += "        <input type=\"text\" name=\"username\" value='" + user.getUsername() + "'><br>\r\n";
//		} else {
//			retval += "        <input type=\"text\" name=\"username\"><br>\r\n";
//		}	
//		if (username != null && username.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>Username must contain 5 to 20 alphanumerical characters</p>";
//		}
//		// E-mail
//		retval += "        <label>Email:</label><br>\r\n";
//		if (invalidTry) {
//			retval += "        <input type=\"email\" name=\"email\" value='" + user.getEmail() + "'><br>\r\n";
//		} else {
//			retval += "        <input type=\"email\" name=\"email\"><br>\r\n";
//		}
//		if (email != null && email.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>Please enter a valid email!</p>";
//		}
//		// Password
//		retval += "        <label>Password:</label><br>\r\n"
//				+ "        <input type=\"password\" name=\"password\"><br>\r\n";
//		if (password != null && password.equals("failure")) {
//			retval += "<p style='color:red;font-size:12px;margin:0;pading:0;'>Password must contain from 8 to 20 characters, at least one letter and at least one digit</p>";
//		}
//		retval += "        <button type=\"submit\">Register</button>\r\n" + "    </form>\r\n" + "</body>\r\n"
//				+ "</html>";

		return "register";
	}

	@PostMapping("/register")
	public void postRegister(@ModelAttribute User user, BindingResult result, HttpServletResponse response, HttpSession session)
			throws IOException {
		StringBuilder queryParameter = new StringBuilder();
		if (!user.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&email=failure");
			} else {
				queryParameter.append("?email=failure");
			}
		}
		
		if (_userService.usernameExistsCheck(user.getUsername()) == 1) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&username=exists");
			} else {
				queryParameter.append("?username=exists");
			}
		}
		
		if (!user.getUsername().matches("^[A-Za-z][A-Za-z0-9_]{4,20}$")) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&username=failure");
			} else {
				queryParameter.append("?username=failure");
			}
		}

		if (!user.getFirstName().matches("^[A-Za-z]+([ -][A-Za-z]+)*$")) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&firstName=failure");
			} else {
				queryParameter.append("?firstName=failure");
			}
		}

		if (!user.getLastName().matches("^[A-Za-z]+([ -][A-Za-z]+)*$")) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&lastName=failure");
			} else {
				queryParameter.append("?lastName=failure");
			}
		}

		if (!user.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
			if (queryParameter.length() > 0) {
				queryParameter.append("&password=failure");
			} else {
				queryParameter.append("?password=failure");
			}
		}

//		if (user.getDateOfBirth().equals(null)) {
//			if (queryParameter.length() > 0) {
//				queryParameter.append("&dateOfBirth=failure");
//			} else {
//				queryParameter.append("?dateOfBirth=failure");
//			}
//		}

		if (queryParameter.length() > 0) {
			response.sendRedirect("/user/register" + queryParameter.toString());
			session.setAttribute("registerFormData", user);
			return;
		}
		
		int res = _userService.addUser(user);
		session.removeAttribute("registerFormData");
		response.sendRedirect("/user/register?status=success");
		return;
	}

	@GetMapping("/profile")
	public String getUserProfile(HttpSession session, Model model) {
		if (session.getAttribute("loggedIn") == null) {
			return "redirect:/";
		}
		
		User loggedInUser = (User)session.getAttribute("loggedIn");
		
		model.addAttribute("reservations", _reservationService.getUserReservations(loggedInUser.getId()));
		
		return "user-profile";
	}
}