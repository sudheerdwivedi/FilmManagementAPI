package com.film.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.film.model.User;
import com.film.websocket.dtos.AppUserDetails;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private final UserService userService;

	@Autowired
	public AppUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

		User user = userService.findUserByUsername(s);
		if (user == null) {
			throw new UsernameNotFoundException("User does not exist!");

		}
		return new AppUserDetails(user);
	}
}
