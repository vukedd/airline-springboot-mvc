package com.project.uwd.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.LoyaltyCard;
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

	@Override
	public List<LoyaltyCardRequest> getLoyaltyCardRequests() {
		return _loyaltyCardRequestRepository.getLoyaltyCardRequests();
	}

	@Override
	public boolean approveLoyaltyCardRequest(Long requestId, Long userId) {
		return _loyaltyCardRequestRepository.approveLoyaltyCardRequest(requestId, userId);
	}

	@Override
	public LoyaltyCardRequest getLoyaltyCardRequestById(Long id) {
		return _loyaltyCardRequestRepository.getLoyaltyCardRequestById(id);
	}

	@Override
	public boolean denyLoyaltyCardRequest(Long requestId) {
		return _loyaltyCardRequestRepository.denyLoyaltyCardRequest(requestId);
	}


}
