package com.project.uwd.repositories.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

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
		for (Location location : locations) {
			location.setDecodedImage(location.getImage() != null ? "data:image/jpeg;base64," + Base64Utils.encodeToString(location.getImage()) : null);
		}
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

		if (location != null) {
			location.setDecodedImage(location.getImage() != null ? "data:image/jpeg;base64," + Base64Utils.encodeToString(location.getImage()) : null);
		}
		
		return location;
	}

	@Override
	public int deleteLocation(Long id) {
		String sql = "DELETE FROM Location WHERE LocationId = ? and LocationId not in (SELECT LocationId FROM Airport);";
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
	public int addLocation(Location location, MultipartFile locationImage) {
		String sql = "INSERT INTO Location(Country, City, Continent, LocationImage) VALUES (?, ?, ?, ?);";
		int res;
		try {
			byte[] image = locationImage.isEmpty() ? null : locationImage.getBytes();
			if (image != null)
				res = jdbcTemplate.update(sql, location.getCountry(), location.getCity(), location.getContinent().ordinal(), image);
			else
				res = 0;
		} catch (EmptyResultDataAccessException | IOException e) {
			System.out.println("An error ocurred while adding the location!");
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
