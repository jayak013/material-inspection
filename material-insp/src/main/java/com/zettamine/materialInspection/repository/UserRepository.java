package com.zettamine.materialInspection.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zettamine.materialInspection.entities.User;

public interface UserRepository extends JpaRepository<User, Serializable> {
	
	
	User findByUserNameAndPassword(String username,String password);
}
