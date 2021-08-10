package com.film.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.film.model.Film;

public interface FilmRepository extends JpaRepository<Film, Integer> {
	  Film findByName(String name);
	}
