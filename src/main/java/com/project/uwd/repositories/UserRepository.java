package com.project.uwd.repositories;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.User;

@Repository
public interface UserRepository {
	int addUser(User user);
	User getUserByUsername(String username);
	int usernameExistsCheck(String username);
}
