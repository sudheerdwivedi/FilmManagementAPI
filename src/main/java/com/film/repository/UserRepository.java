package com.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.film.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    User findUserByUsername(String username);
}
