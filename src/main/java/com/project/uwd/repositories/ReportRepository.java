package com.project.uwd.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository {
	public List<int[]> getReport(LocalDate from, LocalDate to);
	public int getTotalFlightSeats(Long flightId);
	public int getSoldFlightSeats(Long flightId);
	public double getTotalIncomeByFlight(Long flightId);
}	
