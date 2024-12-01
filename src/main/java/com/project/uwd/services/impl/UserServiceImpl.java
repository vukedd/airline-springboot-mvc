package com.project.uwd.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.User;
import com.project.uwd.repositories.UserRepository;
import com.project.uwd.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository _userRepository;
	
	@Override
	public void addUser(User user) {
		_userRepository.addUser(user);
	}
	
}
