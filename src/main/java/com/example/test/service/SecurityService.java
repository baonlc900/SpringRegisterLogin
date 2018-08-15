package com.example.test.service;

public interface SecurityService {
	String findLoggedInUsername();

    void autologin(String username, String password);

}
