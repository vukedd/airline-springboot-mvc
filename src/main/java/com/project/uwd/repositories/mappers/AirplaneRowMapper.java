package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.Airplane;

public class AirplaneRowMapper implements RowMapper<Airplane>{

	@Override
	public Airplane mapRow(ResultSet rs, int rowNum) throws SQLException {
		Airplane airplane = new Airplane();
		airplane.setId(rs.getLong(1));
		airplane.setName(rs.getString(2));
		airplane.setNumberOfColumns(rs.getInt(3));
		airplane.setNumberOfRows(rs.getInt(4));
		
		return airplane;
	}

}
