package com.project.uwd.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.LoyaltyCard;
import com.project.uwd.models.User;
import com.project.uwd.repositories.AuthRepository;
import com.project.uwd.repositories.FlightRepository;
import com.project.uwd.repositories.LoyaltyCardRepository;
import com.project.uwd.repositories.WishlistRepository;
import com.project.uwd.repositories.mappers.UserRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class AuthRepositoryImpl implements AuthRepository{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoyaltyCardRepository _loyaltyCardRepository;
	
	@Autowired
	private WishlistRepository _wishlistRepository;
	
	private UserRowMapper rowMapper;
	
	@PostConstruct
	public void init() {
		rowMapper = new UserRowMapper();
	}
	
	@Override
	public User authenticateUser(String username, String password) {
		String sql = "SELECT * FROM user WHERE username = ? and password = ?;";
		User user;
		try {
			user = jdbcTemplate.queryForObject(sql, rowMapper, username, password);
		} catch (Exception e) {
			return null;
		}
		
		LoyaltyCard card = _loyaltyCardRepository.getLoyaltyCardById(user.getLoyaltyCardId());
		
		if (user != null && card != null) {
			user.setLoyaltyCard(card);
		}
		
		return user;
	}

}
