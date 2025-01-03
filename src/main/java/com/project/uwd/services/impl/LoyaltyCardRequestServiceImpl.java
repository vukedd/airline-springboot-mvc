package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.LoyaltyCardRequest;
import com.project.uwd.repositories.LoyaltyCardRequestRepository;
import com.project.uwd.services.LoyaltyCardRequestService;

@Service
public class LoyaltyCardRequestServiceImpl implements LoyaltyCardRequestService{
	
	@Autowired
	LoyaltyCardRequestRepository _loyaltyCardRequestRepository;
	
	@Override
	public boolean createLoyaltyCardRequest(Long userId) {
		return _loyaltyCardRequestRepository.createLoyaltyCardRequest(userId);
	}

	@Override
	public List<LoyaltyCardRequest> getLoyaltyCardRequestsById(Long userId) {
		return _loyaltyCardRequestRepository.getLoyaltyCardRequestsById(userId);
	}

}
