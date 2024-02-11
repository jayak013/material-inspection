package com.zettamine.materialInspection.service;

import com.zettamine.materialInspection.entities.User;

public interface UserService {
	User getByUserNameAndPassword(String username,String password);
	
	User addUser(User user);
}
