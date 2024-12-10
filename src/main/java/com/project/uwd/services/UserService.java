package com.project.uwd.services;

import org.springframework.stereotype.Service;

import com.project.uwd.models.User;

@Service
public interface UserService {
	int addUser(User user);
	User getUserByUsername(String username);
	int usernameExistsCheck(String username);
}
