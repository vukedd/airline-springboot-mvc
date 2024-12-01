package com.project.uwd.repositories.impl;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Repository;

import com.project.uwd.helpers.CSVResourceProvider;
import com.project.uwd.models.User;
import com.project.uwd.repositories.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Override
	public void addUser(User user) {
		try {
			CSVResourceProvider.getInstance().addUser(user);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
