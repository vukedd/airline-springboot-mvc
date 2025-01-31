package com.project.uwd.repositories.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.LoyaltyCard;
import com.project.uwd.models.User;
import com.project.uwd.models.enums.Role;
import com.project.uwd.repositories.LoyaltyCardRepository;
import com.project.uwd.repositories.UserRepository;
import com.project.uwd.repositories.mappers.UserRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class UserRepositoryImpl implements UserRepository{
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoyaltyCardRepository _loyaltyCardRepository;
	
	private UserRowMapper rowMapper;
	
	@PostConstruct
	public void init() {
		rowMapper = new UserRowMapper();
	}
	
	@Override
	public int addUser(User user) {
		String sql = "INSERT INTO user (Username, Password, Email, FirstName, LastName, DateOfBirth, DateOfRegistration, Role, IsBlocked) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
		user.setRole(Role.Tourist);
		user.setDateOfRegistration(LocalDate.now());
		int res;
		try {
			res = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getDateOfRegistration(), Role.Tourist.ordinal(), 0);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Error");
			res = 0;
		}
		return res;
	}

	@Override
	public User getUserByUsername(String username) {
		String sql = "SELECT * FROM user WHERE username = ?;";
		User user = jdbcTemplate.queryForObject(sql, rowMapper, username);
		
		return user;
	}

	@Override
	public int usernameExistsCheck(String username) {
		String sql = "SELECT COUNT(*) FROM user WHERE username = ?;";
		int res = jdbcTemplate.queryForObject(sql, Integer.class, username);
		
		return (int)res;
	}

	@Override
	public User getUserById(Long id) {
		User user = null;
		String sql = "SELECT * FROM user WHERE UserId = ?;";
		
		try {
			user = jdbcTemplate.queryForObject(sql, rowMapper, id);
		} catch (Exception e) {
			System.out.println("Error");
		}
		
		LoyaltyCard card = _loyaltyCardRepository.getLoyaltyCardById(user.getLoyaltyCardId());
		
		if (user != null && card != null) {
			user.setLoyaltyCard(card);
		}
		
		return user;
	}

	@Override
	public boolean editUserData(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth, String email) {
		boolean successfullyChanged = false;
		String sql = "UPDATE user SET Username = ?, FirstName = ?, LastName = ?, Email = ?, DateOfBirth = ? WHERE UserId = ?";
		
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(sql, username, firstName, lastName, email, dateOfBirth, id);
		} catch (Exception e){
			System.out.println("Error while editing user data!");
		}
		
		if (rowsAffected == 1) {
			successfullyChanged = true;
		}
		
		return successfullyChanged;
	}

	@Override
	public int emailExistsCheck(String email) {
		String sql = "SELECT COUNT(*) FROM user WHERE email = ?;";
		int res = jdbcTemplate.queryForObject(sql, Integer.class, email);
		
		return (int)res;
	}

	@Override
	public boolean editUserPassword(Long id, String newPassword) {
		boolean successfullyChanged = false;
		String sql = "UPDATE user SET password = ? WHERE UserId = ?;";
		
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(sql, newPassword, id);
		} catch (Exception e) {
			System.out.println("Error while editing user password!");
		}
		
		if (rowsAffected == 1) {
			successfullyChanged = true;
		}
		
		return successfullyChanged;
	}

	@Override
	public List<User> getAllUsers() {
		String sql = "SELECT * FROM user;";
		List<User> users = new ArrayList<User>();
		try {
			users = jdbcTemplate.query(sql, rowMapper);
		} catch (Exception e) {
			System.out.println("An error occurred while fetching users!");
		}
		return users;
	}

	@Override
	public boolean blockUserById(Long id) {
		String sql = "UPDATE user SET IsBlocked = 1 WHERE UserId = ?;";
		
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			System.out.println("An error occurred while blocking user!");
		}
		
		if (rowsAffected != 0) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean unblockUserById(Long id) {
		String sql = "UPDATE user SET IsBlocked = 0 WHERE UserId = ?;";
		
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			System.out.println("An error occurred while unblocking user!");
		}
		
		if (rowsAffected != 0) {
			return true;
		}
		
		return false;
	}
	
}
