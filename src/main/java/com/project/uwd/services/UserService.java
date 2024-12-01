package com.project.uwd.services;

import org.springframework.stereotype.Service;

import com.project.uwd.models.User;

@Service
public interface UserService {
	void addUser(User user);
}
