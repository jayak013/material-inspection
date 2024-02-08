package com.zettamine.materialInspection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zettamine.materialInspection.entities.User;
import com.zettamine.materialInspection.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public User getByUserNameAndPassword(String username, String password) {
		return userRepo.findByUserNameAndPassword(username, password);
	}

}
