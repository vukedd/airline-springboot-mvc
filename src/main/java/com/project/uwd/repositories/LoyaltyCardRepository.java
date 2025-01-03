package com.project.uwd.repositories;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.LoyaltyCard;

@Repository
public interface LoyaltyCardRepository {
	public LoyaltyCard getLoyaltyCardById(Long id);
}
