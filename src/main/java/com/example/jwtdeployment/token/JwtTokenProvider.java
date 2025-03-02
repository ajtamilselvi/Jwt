package com.example.jwtdeployment.token;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private String jwtExpirationString;

	private long jwtExpirationDate;

	@PostConstruct
	public void init() {
	    jwtExpirationDate = Long.parseLong(jwtExpirationString);
	}
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
				.subject(username)
				.issuedAt(new Date())
				.expiration(expireDate)
				.signWith(Key())
				.compact();
		
		return token;
	}

	private Key Key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	
	public String getUserName(String token) {
		return Jwts.parser()
				.verifyWith((SecretKey) Key())
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		Jwts.parser()
			.verifyWith((SecretKey) Key())
			.build()
			.parse(token);
		return true;
	}
}
