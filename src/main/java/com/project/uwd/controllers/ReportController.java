package com.project.uwd.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.services.ReportService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/report")
public class ReportController {

	@Autowired
	private ReportService _reportService;
	
	@GetMapping("/")
	public String getReports(@RequestParam(required=false) LocalDate dateFrom, @RequestParam(required=false) LocalDate dateTo, Model model, HttpSession session) {
		User user = (User)session.getAttribute("loggedIn");
		if (user == null || user.getRole().compareTo(Role.Admin) != 0) {
			return "redirect:/auth/login";
		}
		
		session.setAttribute("reportGenerated", false);
		
		List<int[]> result = null;
		if (dateFrom != null && dateTo != null) {
			result = _reportService.getReport(dateFrom, dateTo);
			session.setAttribute("reportGenerated", true);
			if (result != null) {
				int[] reportTotals = _reportService.calculateTotals(result);
				model.addAttribute("dataSet", result);
				model.addAttribute("sumSeats", reportTotals[0]);
				model.addAttribute("incomeSum", reportTotals[1]);
			}
		} else {
			model.addAttribute("dataSet", null);
		}
		
		return "reports";
	}
}
