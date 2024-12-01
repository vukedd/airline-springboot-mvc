package com.project.uwd.repositories;

import org.springframework.stereotype.Repository;

import com.project.uwd.models.User;

@Repository
public interface UserRepository {
	void addUser(User user);
}
