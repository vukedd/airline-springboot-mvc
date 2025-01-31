package com.project.uwd.repositories.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Airplane;
import com.project.uwd.repositories.AirplaneRepository;
import com.project.uwd.repositories.mappers.AirplaneRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class AirplaneRepositoryImpl implements AirplaneRepository{
	
	@Autowired
	JdbcTemplate _jdbcTemplate;
	
	private AirplaneRowMapper _airplaneRowMapper;
	
	@PostConstruct
	public void init() {
		_airplaneRowMapper = new AirplaneRowMapper();
	}
	
	@Override
	public List<Airplane> getAllAirplanes() {
		String sql = "SELECT * FROM airplane;";
		List<Airplane> airplanes = _jdbcTemplate.query(sql, _airplaneRowMapper);
		return airplanes;
	}

	@Override
	public Airplane getAirplaneById(Long id) {
		String sql = "SELECT * FROM airplane WHERE AirplaneId = ?";
		Airplane airplane;
		try {
			airplane = _jdbcTemplate.queryForObject(sql, _airplaneRowMapper, id);
		} catch (Exception e) {
			airplane = null;
		}
		return airplane;
	}

	@Override
	public int addAirplane(Airplane airplane) {
		int res = 0;
		String sql = "INSERT INTO airplane(name, numberOfColumns, numberOfRows) VALUES (?, ?, ?);";
		
		try {
			res = _jdbcTemplate.update(sql, airplane.getName(), airplane.getNumberOfColumns(), airplane.getNumberOfRows());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error;");
		}
		
		return res;
	}

	@Override
	public int editAirplane(Long id, Airplane airplane) {
		int res = 0;
		String sql = "UPDATE airplane SET name = ?, numberOfColumns = ?, numberOfRows = ? WHERE airplaneId = ?";
		
		try {
			res = _jdbcTemplate.update(sql, airplane.getName(), airplane.getNumberOfColumns(), airplane.getNumberOfRows(), id);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error;");
		}
		
		return res;
	}

	@Override
	public int deleteAirplaneById(Long id) {
		int res = 0;
		String sql = "DELETE FROM airplane WHERE AirplaneId = ? and AirplaneId not in (SELECT AirplaneId FROM flight WHERE AirplaneId IS NOT NULL);";
		
		try {
			res = _jdbcTemplate.update(sql, id);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error;");
		}
		
		return res;
	}

	@Override
	public List<Airplane> getAvailableAirplanes() {
		String sql = "SELECT airplane.AirplaneId, Name, NumberOfColumns, NumberOfRows FROM airplane "
				+ "LEFT JOIN flight ON flight.AirplaneId = airplane.AirplaneId "
				+ "WHERE DateOfDeparture < current_date() OR (DateOfDeparture > current_date() AND IsCancelled = 1);";
		List<Airplane> airplanes;
		
//		try {
			airplanes = _jdbcTemplate.query(sql, _airplaneRowMapper);
//		} catch (Exception e) {
//			airplanes = null; 
//		}
		
		return airplanes;
	}

}
