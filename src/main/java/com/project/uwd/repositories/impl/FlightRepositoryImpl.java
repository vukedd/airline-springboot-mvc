package com.project.uwd.repositories.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Flight;
import com.project.uwd.repositories.AirplaneRepository;
import com.project.uwd.repositories.AirportRepository;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.repositories.mappers.FlightRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class FlightRepositoryImpl implements FlightRepository {
	
	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	@Autowired
	private AirportRepository _airportRepository;

	@Autowired
	private AirplaneRepository _airplaneRepository;
	
	private FlightRowMapper _flightRowMapper;
	
	@PostConstruct
	public void init() {
		_flightRowMapper = new FlightRowMapper();
	}
	
	@Override
	public List<Flight> getAllFlights() {
		String sql = "SELECT * FROM Flight WHERE IsCancelled = 0 AND DateOfDeparture > current_date();";
		List<Flight> flights;
		
		try {
			flights = _jdbcTemplate.query(sql, _flightRowMapper);
		} catch (Exception e) {
			flights = null;
		}
		
		if (flights != null) {
			for (Flight flight : flights) {
				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
				flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
				flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
			}
		}
		
		return flights;
	}

	@Override
	public Flight getFlightById(Long id) {
		String sql = "SELECT * FROM Flight WHERE flightId = ?;";
		Flight flight;
		try {
			flight = _jdbcTemplate.queryForObject(sql, _flightRowMapper, id);
		} catch (Exception e) {
			flight = null;
		}
		
		if (flight != null) {
			flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
			flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
			flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
		}
		
		return flight;
	}

	@Override
	public int deleteFlight(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editFlight(Long id, Flight flight) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int createFlight(Flight flight) {
		String sql = "INSERT INTO Flight(DateOfDeparture, Duration, TicketPrice, DepartureId, DestinationId, AirplaneId, IsCancelled) VALUES (?, ?, ?, ?, ?, ?, 0);";
		int res;
		
		try {
			res = _jdbcTemplate.update(sql, LocalDateTime.of(flight.getDateOfDeparture(), flight.getTimeOfDeparture()), flight.getDuration(), flight.getTicketPrice(), flight.getDepartureId(), flight.getDestinationId(), flight.getAirplaneId());
		} catch (Exception e) {
			res = 0;
		}
		
		return res;
	}

	@Override
	public int cancelFlight(Long id) {
		String sql = "UPDATE Flight SET IsCancelled = 1 WHERE FlightId = ?;";
		int res;
		
		try {
			res = _jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			res = 0;
		}
		
		return res;
	}

	@Override
	public List<Flight> searchFlights(String departure, String destination, LocalDate dateOfDeparture) {
		String sql;
		List<Flight> flights;
		String departureChecker = !departure.equals(null) ? departure + "%" : "%";
		String destinationChecker = !destination.equals(null) ? destination + "%" : "%";

		if (dateOfDeparture == null) {
			sql = "SELECT *\r\n"
					+ "FROM Flight f\r\n"
					+ "LEFT JOIN Airport departure ON departure.AirportId = f.DepartureId\r\n"
					+ "LEFT JOIN Location location1 ON departure.LocationId = location1.LocationId\r\n"
					+ "LEFT JOIN Airport destination ON destination.AirportId = f.DestinationId\r\n"
					+ "LEFT JOIN Location location2 ON destination.LocationId = location2.LocationId\r\n"
					+ "WHERE (departure.AirportCode like ? OR location1.country like ? OR location1.city like ?) AND (destination.AirportCode like ? OR location2.country like ? OR location2.city like ?) AND IsCancelled = 0 AND DateOfDeparture > current_date();";
			try {
				flights = _jdbcTemplate.query(sql, _flightRowMapper, departureChecker, departureChecker, departureChecker, destinationChecker, destinationChecker, destinationChecker);
			} catch (Exception e) {
				flights = null;
			}
			
		} else {
			sql = "SELECT *\r\n"
					+ "FROM Flight f\r\n"
					+ "LEFT JOIN Airport departure ON departure.AirportId = f.DepartureId\r\n"
					+ "LEFT JOIN Location location1 ON departure.LocationId = location1.LocationId\r\n"
					+ "LEFT JOIN Airport destination ON destination.AirportId = f.DestinationId\r\n"
					+ "LEFT JOIN Location location2 ON destination.LocationId = location2.LocationId\r\n"
					+ "WHERE (departure.AirportCode like ? OR location1.country like ? OR location1.city like ?) AND (destination.AirportCode like ? OR location2.country like ? OR location2.city like ?) AND IsCancelled = 0 AND DateOfDeparture > current_date() and DateOfDeparture > ?;";
			
			try {
				flights = _jdbcTemplate.query(sql, _flightRowMapper, departureChecker, departureChecker, departureChecker, destinationChecker, destinationChecker, destinationChecker, dateOfDeparture);
			} catch (Exception e) {
				flights = null;
			}
		}
		
		if (flights != null) {
			for (Flight flight : flights) {
				flight.setAirplane(_airplaneRepository.getAirplaneById(flight.getAirplaneId()));
				flight.setDeparture(_airportRepository.getAirportById(flight.getDepartureId()));
				flight.setDestination(_airportRepository.getAirportById(flight.getDestinationId()));
			}
		}
		
		return flights;
	}

	@Override
	public int numberOfAvailableSpotsByFlight(Long flightId) {
		String sql = "SELECT ifnull((airplane.numberOfRows * airplane.numberOfColumns) - count(*), 0) 'Available spots' FROM Airplane, Flight, Ticket WHERE Airplane.airplaneId = Flight.airplaneId and Flight.flightId = Ticket.flightId and Flight.flightId = ?;";
		Integer numberOfFreeSeats;
		try {
			numberOfFreeSeats = _jdbcTemplate.queryForObject(sql, new Object[] {flightId}, Integer.class);
		} catch (Exception e) {
			return -1;
		}
		
		return numberOfFreeSeats;
	}

}
