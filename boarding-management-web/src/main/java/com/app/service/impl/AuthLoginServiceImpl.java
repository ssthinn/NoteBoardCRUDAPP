package com.app.service.impl;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.service.AuthLoginService;

@Service
public class AuthLoginServiceImpl implements AuthLoginService {

	@Value("${com.app.username}")
	private String username;
	@Value("${com.app.password}")
	private String userpassword;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean isAuthenticated(String name, String password) {

		logger.info("Invoke Authentication Service with name : {} - {}", username, LocalDate.now());
		if (username.equals(name) && userpassword.equals(password)) {
			logger.info("Valid User : {}", username);
			return true;
		}

		logger.info("Invalid User : {}", username);
		return false;

	}

}
