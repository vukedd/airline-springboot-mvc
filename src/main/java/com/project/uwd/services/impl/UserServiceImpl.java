package com.project.uwd.services.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.uwd.models.User;
import com.project.uwd.repositories.UserRepository;
import com.project.uwd.services.UserService;
import com.project.uwd.services.WishlistService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository _userRepository;
	
	@Autowired
	WishlistService _wishlistService;
	
	@Override
	public int addUser(User user) {
		return _userRepository.addUser(user);
	}

	@Override
	public User getUserByUsername(String username) {
		return _userRepository.getUserByUsername(username);
	}

	@Override
	public int usernameExistsCheck(String username) {
		return _userRepository.usernameExistsCheck(username);
	}

	@Override
	public User getUserById(Long id) {
		User user = _userRepository.getUserById(id);
		if (user != null) {
			user.setWishlist(_wishlistService.getWishlistByUserId(user.getId()));
		}
		return user;
	}

	@Override
	public boolean editUserData(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth, String email) {
		return _userRepository.editUserData(id, username, firstName, lastName, dateOfBirth, email);
	}

	@Override
	public int emailExistsCheck(String email) {
		return _userRepository.emailExistsCheck(email);
	}

	@Override
	public boolean editUserPassword(Long id, String newPassword) {
		return _userRepository.editUserPassword(id, newPassword);
	}

	@Override
	public List<User> getAllUsers() {
		return _userRepository.getAllUsers();
	}

	@Override
	public boolean blockUserById(Long id) {
		return _userRepository.blockUserById(id);
	}

	@Override
	public boolean unblockUserById(Long id) {
		return _userRepository.unblockUserById(id);
	}	
}
