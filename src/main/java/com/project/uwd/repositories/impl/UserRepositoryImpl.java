package com.project.uwd.repositories.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.repositories.UserRepository;
import com.project.uwd.repositories.mappers.UserRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private UserRowMapper rowMapper;
	
	@PostConstruct
	public void init() {
		rowMapper = new UserRowMapper();
	}
	
	@Override
	public int addUser(User user) {
		String sql = "INSERT INTO User(Username, Password, Email, FirstName, LastName, DateOfBirth, DateOfRegistration,Role) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
		user.setRole(Role.Tourist);
		user.setDateOfRegistration(LocalDate.now());
		int res;
		try {
			res = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getDateOfRegistration(), Role.Tourist.ordinal());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error");
			res = 0;
		}
		return res;
	}

	@Override
	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM User WHERE username = ?;";
		User user = jdbcTemplate.queryForObject(sql, rowMapper, username);
		
		return user;
	}

	@Override
	public int usernameExistsCheck(String username) {
		String sql = "SELECT COUNT(*) FROM User WHERE username = ?;";
		int res = jdbcTemplate.queryForObject(sql, Integer.class, username);
		
		return (int)res;
	}

}
