package com.project.uwd.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.User;

@Repository
public interface UserRepository {
	int addUser(User user);
	User getUserByUsername(String username);
	int usernameExistsCheck(String username);
	int emailExistsCheck(String email);
	public User getUserById(Long id);
	public boolean editUserData(Long id, String username, String firstName, String lastName, LocalDate dateOfBirth, String email);
	public boolean editUserPassword(Long id, String newPassword);
	public List<User> getAllUsers();
	public boolean blockUserById(Long id);
	public boolean unblockUserById(Long id);
}
