package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Location;
import com.project.uwd.models.enums.Continent;

public class LocationRowMapper implements RowMapper<Location>{

	@Override
	public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
		Location location = new Location();
		location.setId(rs.getLong(1));
		location.setCountry(rs.getString(2));
		location.setCity(rs.getString(3));
		location.setContinent(Continent.values()[rs.getInt(4)]);
		
		return location;
	}
}
