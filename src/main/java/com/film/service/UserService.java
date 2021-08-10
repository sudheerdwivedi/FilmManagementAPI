package com.film.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.film.model.User;
import com.film.repository.UserRepository;
import com.film.websocket.dtos.UserDto;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByUsername(String username){
        return userRepo.findUserByUsername(username);
    }

    public void saveUser(UserDto dto){
        userRepo.save(new User(dto, passwordEncoder));
    }
}
