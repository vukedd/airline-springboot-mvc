package com.project.uwd.repositories.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.Location;
import com.project.uwd.repositories.LocationRepository;
import com.project.uwd.repositories.mappers.LocationRowMapper;

import jakarta.annotation.PostConstruct;

@Repository("LocationRepositoryDBImpl")
public class LocationRepositoryImpl implements LocationRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private LocationRowMapper rowMapper;
	
	@PostConstruct
	public void init() {
		rowMapper = new LocationRowMapper();
	}
	
	@Override
	public List<Location> getLocations() {
		String sql = "SELECT * FROM Location;";
		List<Location> locations = jdbcTemplate.query(sql, rowMapper);
		return locations;
	}

	@Override
	public Location getLocation(Long id) {
		String sql = "SELECT * FROM Location WHERE LocationId = ?;";

		Location location;
		try {
			location = jdbcTemplate.queryForObject(sql, rowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			location = null;
		}

		
		return location;
	}

	@Override
	public int deleteLocation(Long id) {
		String sql = "DELETE FROM Location WHERE LocationId = ?;";
		int res;
		try {
			res = jdbcTemplate.update(sql, id);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error;");
			res = 0;
		}
		return res;
	}

	@Override
	public int addLocation(Location location) {
		String sql = "INSERT INTO Location(Country, City, Continent) VALUES (?, ?, ?);";
		int res;
		try {
			res = jdbcTemplate.update(sql, location.getCountry(), location.getCity(), location.getContinent().ordinal());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error;");
			res = 0;
		}
		return res;
	}

	@Override
	public int editLocation(Long id, Location location) {
		String sql = "UPDATE Location SET City = ?, Country = ?, Continent = ? WHERE LocationId = ?;";
		int res;
		try {
			res = jdbcTemplate.update(sql, location.getCity(), location.getCountry(), location.getContinent().ordinal(), id);		
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error;");
			res = 0;
		}
		return res;
	}

}
