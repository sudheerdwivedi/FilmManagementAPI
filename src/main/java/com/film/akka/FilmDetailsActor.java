package com.film.akka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.film.model.Film;
import com.film.service.FilmService;
import com.film.util.FilmUtil;

import akka.actor.UntypedAbstractActor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FilmDetailsActor extends UntypedAbstractActor {

    @Autowired    
    private FilmService filmService;


    
	@Override
	public void onReceive(Object message) {
		Map<String, Object> actionMap = (HashMap<String, Object>) message;
		System.out.println("::::OnReceive method of FilmDetailActor and parameters are:" + actionMap);
		if (FilmUtil.GET_ALL_FILMS_URL.equals(actionMap.get(FilmUtil.ACTION_URL))) {
			getSender().tell(filmService.getAllFilms(), getSelf());
		} else if (FilmUtil.GET_FILM_BY_NAME_URL.equals(actionMap.get(FilmUtil.ACTION_URL))) {
			String filmName = (String) actionMap.get(FilmUtil.ACTION_PARAMETERS);
			getSender().tell(filmService.getFilmByName(filmName), getSelf());
		} else if (FilmUtil.ADD_COMMENTS_URL.equals(actionMap.get(FilmUtil.ACTION_URL))) {
			String nameAndComment = (String) actionMap.get(FilmUtil.ACTION_PARAMETERS);
			String arr[] =  nameAndComment.split(":");
			getSender().tell(filmService.addComment(arr[0], arr[1]), getSelf());
		} else if (FilmUtil.ADD_FILM_URL.equals(actionMap.get(FilmUtil.ACTION_URL))) {
			Film film = (Film) actionMap.get(FilmUtil.ACTION_PARAMETERS);
			filmService.saveOrUpdate(film);
			getSender().tell("Success", getSelf());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
}