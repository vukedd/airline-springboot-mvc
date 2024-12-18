package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.project.uwd.models.Airport;
import com.project.uwd.repositories.LocationRepository;
import com.project.uwd.repositories.impl.LocationRepositoryImpl;

@Component
public class AirportRowMapper implements RowMapper<Airport>{
	
	
	@Override
	public Airport mapRow(ResultSet rs, int rowNum) throws SQLException {
		Airport airport = new Airport();
		airport.setId(rs.getLong(1));
		airport.setCode(rs.getString(2));
		airport.setLocationId(rs.getLong(3));
		return airport;
	}

}
