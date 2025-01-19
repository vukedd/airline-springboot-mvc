package com.project.uwd.repositories.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Discount;
import com.project.uwd.models.Flight;
import com.project.uwd.repositories.AirplaneRepository;
import com.project.uwd.repositories.AirportRepository;
import com.project.uwd.repositories.ReportRepository;
import com.project.uwd.repositories.mappers.FlightRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class ReportRepositoryImpl implements ReportRepository {

	@Autowired
	JdbcTemplate _jdbcTemplate;
	
	@Autowired
	AirplaneRepository _airplaneRepository;
	
	@Autowired
	AirportRepository _airportRepository;
	
	FlightRowMapper _flightRowMapper;
	
	@PostConstruct
	public void init() {
		_flightRowMapper = new FlightRowMapper();
	}
	
	@Override
	public List<int[]> getReport(LocalDate from, LocalDate to) {
		List<int[]> result = new ArrayList<>();
		
		String sql = "SELECT *\r\n"
				+ "FROM flight\r\n"
				+ "WHERE DateOfDeparture < ? and DateOfDeparture > ? and IsCancelled = 0;";
		
		List<Flight> flights;
		try {
			flights = _jdbcTemplate.query(sql, _flightRowMapper, to, from);
		} catch (Exception e) {		
			flights = new ArrayList<>();
		}
		
//		if (flights != null) {
//			for (Flight flight : flights) {
//				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
//				flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
//				flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
//			}
//		}
		
		for (Flight f : flights) {
			int[] data = new int[4];
			data[0] = f.getId().intValue();
			data[1] = getTotalFlightSeats(f.getId());
			data[2] = getSoldFlightSeats(f.getId());
			data[3] = (int)getTotalIncomeByFlight(f.getId());
			result.add(data);
		}
		
//		for (int[] dataSet : result) {
//			System.out.println("row");
//			for (int data : dataSet) {
//				System.out.println(data);
//			}
//		}
		if (result.size() < 1)
			return null;
			
		return result;
	}

	@Override
	public int getTotalFlightSeats(Long flightId) {
		String sql = "SELECT DISTINCT airplane.NumberOfRows * airplane.numberOfColumns 'Total seats'\r\n"
				+ "FROM Airplane \r\n"
				+ "LEFT JOIN Flight ON Airplane.airplaneId = Flight.airplaneId\r\n"
				+ "LEFT JOIN Ticket ON Flight.flightId = Ticket.flightId\r\n"
				+ "WHERE Flight.flightId = ?;";
		
		Integer totalSeats;
		try {
			totalSeats = _jdbcTemplate.queryForObject(sql, new Object[] {flightId} ,Integer.class);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching total flight seats!");
			totalSeats = 0;
		}
		return totalSeats;
	}

	@Override
	public int getSoldFlightSeats(Long flightId) {
		String sql = "SELECT \r\n"
				+ "(SELECT DISTINCT airplane.NumberOfRows * airplane.numberOfColumns 'Total seats'\r\n"
				+ "FROM Airplane \r\n"
				+ "LEFT JOIN Flight ON Airplane.airplaneId = Flight.airplaneId\r\n"
				+ "LEFT JOIN Ticket ON Flight.flightId = Ticket.flightId\r\n"
				+ "WHERE Flight.flightId = ?)\r\n"
				+ "-\r\n"
				+ "(SELECT airplane.numberOfRows * airplane.numberOfColumns - count(ticketId) 'Free seats'\r\n"
				+ "FROM Airplane LEFT JOIN Flight ON Airplane.airplaneId = Flight.airplaneId\r\n"
				+ "LEFT JOIN Ticket ON Flight.flightId = Ticket.flightId WHERE Flight.flightId = ?) 'Taken seats';";
		
		int soldSeats;
		try {
			soldSeats = _jdbcTemplate.queryForObject(sql, new Object[] {flightId, flightId}, Integer.class);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching sold seats!");
			soldSeats = 0;
		}
		
		return soldSeats;
	}

	@Override
	public double getTotalIncomeByFlight(Long flightId) {
		String sql = "SELECT SUM(TicketPrice) 'Total income'\r\n"
				+ "FROM flight\r\n"
				+ "LEFT JOIN Ticket on flight.flightId = Ticket.flightId\r\n"
				+ "LEFT JOIN Discount on flight.flightId = Discount.flightId\r\n"
				+ "WHERE flight.FlightId = ?;";
		
		Double totalIncome;
		try {
			totalIncome = _jdbcTemplate.queryForObject(sql, new Object[] {flightId}, Double.class);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching total income!");
			totalIncome = 0.0;
		}
		
		return totalIncome;
	}

}
