package com.example.jwtdeployment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwtdeployment.dto.JwtAuthResponse;
import com.example.jwtdeployment.dto.LoginDto;
import com.example.jwtdeployment.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
	}
}
