package com.film.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.film.model.Comment;
import com.film.model.Film;
import com.film.repository.FilmRepository;

@Service
@Transactional
public class FilmService {

	@Autowired
	FilmRepository filmRepository;

	
	public List<Film> getAllFilms() {
		List<Film> films = filmRepository.findAll();
		for (Film film : films) {
			Hibernate.initialize(film.getComments());
			Hibernate.initialize(film.getGenere());
		}
		 return films;
	}

	public Film getFilmByName(String name) {
		Film film = filmRepository.findByName(name);
		if(film!=null) {
			Hibernate.initialize(film.getComments());
			Hibernate.initialize(film.getGenere());
		}
		return film;
	}

	
	public String addComment(String filmName, String comment) {
		Film film = filmRepository.findByName(filmName);
		if (film != null) {
			List<Comment> commnents = film.getComments();
			if (commnents != null) {
				commnents = new ArrayList<Comment>();
			}
			Comment newComment = new Comment();
			newComment.setComment(comment);
			newComment.setFilm(film);
			commnents.add(newComment);
			film.setComments(commnents);
			filmRepository.save(film);
		}
		return "Comment added Successfully!";
	}
	
	public void saveOrUpdate(Film film) {
		filmRepository.save(film);
	}

	public void delete(int id) {
		filmRepository.deleteById(id);
	}
}