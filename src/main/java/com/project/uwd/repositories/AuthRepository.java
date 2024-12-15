package com.project.uwd.repositories;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.User;

@Repository
public interface AuthRepository {
	public User authenticateUser(String username, String password);
}
