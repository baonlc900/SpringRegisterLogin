package com.example.test.service;

import com.example.test.model.User;

public interface UserService {

	public User findByUsername(String userName);
	
    void save(User user);
	
}
