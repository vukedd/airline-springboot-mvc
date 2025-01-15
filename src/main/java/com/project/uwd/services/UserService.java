package com.project.uwd.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.uwd.models.User;

@Service
public interface UserService {
	int addUser(User user);
	User getUserByUsername(String username);
	int usernameExistsCheck(String username);
	int emailExistsCheck(String email);
	User getUserById(Long id);
	boolean editUserData(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth,String email);
	boolean editUserPassword(Long id, String newPassword);
	List<User> getAllUsers();
}
