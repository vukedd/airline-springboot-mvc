package com.project.uwd.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Airport;
import com.project.uwd.repositories.AirportRepository;
import com.project.uwd.repositories.LocationRepository;
import com.project.uwd.repositories.mappers.AirportRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class AirportRepositoryImpl implements AirportRepository{

	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	@Autowired
	private LocationRepository _locationRepository;
	
	private AirportRowMapper _airportRowMapper;
	
	@PostConstruct
	public void init() {
		_airportRowMapper = new AirportRowMapper();
	}
	
	@Override
	public List<Airport> getAllAiports() {
		String sql = "SELECT * FROM Airport;";
		List<Airport> airports = _jdbcTemplate.query(sql, _airportRowMapper);
		for (Airport a : airports) {
			a.setLocation(_locationRepository.getLocation(a.getLocationId()));
		}
		
		return airports;
	}

	@Override
	public Airport getAirportById(Long id) {
		String sql = "SELECT * FROM Airport WHERE AirportId = ?;";
		Airport airport;
		try {
			airport = _jdbcTemplate.queryForObject(sql, _airportRowMapper, id);

		} catch (Exception e) {
			airport = null;
		}
		
		if (airport != null) {
			airport.setLocation(_locationRepository.getLocation(airport.getLocationId()));
		}
		
		return airport;
	}

	@Override
	public int addAirport(Airport airport) {
		String sql = "INSERT INTO Airport(AirportCode, LocationId) VALUES (?, ?);";
		int res = 0;
		try {
			res = _jdbcTemplate.update(sql, airport.getCode(), airport.getLocationId());
		} catch (Exception e) {
			res = 0;
		}
		
		return res;
	}

	@Override
	public int deleteAirport(Long id) {
		String sql = "DELETE FROM Airport WHERE AirportId = ? and AirportId not in (SELECT DepartureId FROM Flight WHERE DepartureId IS NOT NULL) and AirportId not in (SELECT DestinationId FROM Flight WHERE DestinationId IS NOT NULL);";
		int res = 0;

		try {
			res = _jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			res = 0;
		}
		
		return res;
	}

	@Override
	public int editAirport(Long id, Airport airport) {
		String sql = "UPDATE Airport SET LocationId = ?, AirportCode = ? WHERE AirportId = ?";
		int res = 0;

		try {
			res = _jdbcTemplate.update(sql, airport.getLocationId(), airport.getCode(), id);
		} catch (Exception e) {
			res = 0;
		}
		
		return res;
	}

}
