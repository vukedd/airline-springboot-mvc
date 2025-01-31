package com.project.uwd.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.uwd.models.LoyaltyCard;
import com.project.uwd.repositories.LoyaltyCardRepository;
import com.project.uwd.repositories.mappers.LoyaltyCardRowMapper;

import jakarta.annotation.PostConstruct;

@Repository
public class LoyaltyCardRepositoryImpl implements LoyaltyCardRepository{

	@Autowired
	private JdbcTemplate _jdbcTemplate;
	
	private LoyaltyCardRowMapper _rowMapper;
	
	@PostConstruct
	public void init() {
		_rowMapper = new LoyaltyCardRowMapper();
	}
	
	@Override
	public LoyaltyCard getLoyaltyCardById(Long id) {
		String sql = "SELECT * FROM loyaltycard WHERE LoyaltyCardId = ?;";
		LoyaltyCard card = null;
		
		try {
			card = _jdbcTemplate.queryForObject(sql, _rowMapper, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return card;
	}
	
}
