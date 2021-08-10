package com.film.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.film.model.User;

import java.util.Collections;

@Service
public class WebSocketAuthenticatorService {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authManager;

	public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(String username, String password)
			throws AuthenticationException {

		if (username == null || username.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Username is null or empty!");
		}

		if (password == null || password.trim().isEmpty()) {
			throw new AuthenticationCredentialsNotFoundException("Password is null or empty!");
		}

		User user = userService.findUserByUsername(username);

		if (user == null) {
			throw new AuthenticationCredentialsNotFoundException("User not found!");
		}

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password,
				Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority())));

		// verify that the credentials are valid
		authManager.authenticate(token);
		// Erase the password in the token after verifying
		token.eraseCredentials();
		return token;

	}

}