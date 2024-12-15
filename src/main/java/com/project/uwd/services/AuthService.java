package com.project.uwd.services;

import org.springframework.stereotype.Service;

import com.project.uwd.models.User;

@Service
public interface AuthService {
	public User authenticateUser(String username, String password);
}
