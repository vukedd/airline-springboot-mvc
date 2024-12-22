package com.project.uwd.repositories.impl;

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
		String sql = "SELECT * FROM Flight;";
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
		String sql = "INSERT INTO Flight(DateOfDeparture, Duration, TicketPrice, DepartureId, DestinationId, AirplaneId) VALUES (?, ?, ?, ?, ?, ?);";
		int res;
		
		try {
			res = _jdbcTemplate.update(sql, LocalDateTime.of(flight.getDateOfDeparture(), flight.getTimeOfDeparture()), flight.getDuration(), flight.getTicketPrice(), flight.getDepartureId(), flight.getDestinationId(), flight.getAirplaneId());
		} catch (Exception e) {
			res = 0;
		}
		
		return res;
	}

}
