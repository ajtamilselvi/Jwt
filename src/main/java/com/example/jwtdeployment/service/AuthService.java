package com.example.jwtdeployment.service;

import com.example.jwtdeployment.dto.LoginDto;

public interface AuthService {
	String login(LoginDto loginDto);
}
