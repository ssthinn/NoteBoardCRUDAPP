package com.app.service;

public interface AuthLoginService {
	
	boolean isAuthenticated(String username, String password);
}
