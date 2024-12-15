package com.project.uwd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.User;
import com.project.uwd.repositories.AuthRepository;
import com.project.uwd.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	AuthRepository _authRepository;
	
	@Override
	public User authenticateUser(String username, String password) {
		return _authRepository.authenticateUser(username, password);
	}

}
