package com.project.uwd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.Flight;
import com.project.uwd.models.User;
import com.project.uwd.repositories.AuthRepository;
import com.project.uwd.repositories.WishlistRepository;
import com.project.uwd.services.AuthService;
import com.project.uwd.services.WishlistService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthRepository _authRepository;
	
	@Autowired
	WishlistService _wishlistService;
	
	@Override
	public User authenticateUser(String username, String password) {
		User user = _authRepository.authenticateUser(username, password);
		if (user != null) {
			user.setWishlist(_wishlistService.getWishlistByUserId(user.getId()));
		}
		
		return user;
	}

}
