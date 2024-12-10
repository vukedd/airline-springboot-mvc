package com.project.uwd.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong(1));
		user.setUsername(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setEmail(rs.getString(4));
		user.setFirstName(rs.getString(5));
		user.setLastName(rs.getString(6));
		user.setDateOfBirth(rs.getDate(7).toLocalDate());
		user.setDateOfRegistration(rs.getDate(8).toLocalDate());
		user.setRole(Role.values()[rs.getInt(9)]);
		return user;
	}

}
