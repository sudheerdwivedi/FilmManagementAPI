package com.film.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.film.akka.ActorsProducer;
import com.film.model.Comment;
import com.film.model.Film;
import com.film.model.User;
import com.film.util.FilmUtil;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

@RestController
public class MovieController {

	@Autowired
	private ActorsProducer actorsProducer;

	@MessageMapping(value = "/films/create")
	@SendTo("/topic/messages")
	public String addFilm(@RequestBody Film film) throws TimeoutException, InterruptedException {
		ActorRef filmDetailsActorRef = actorsProducer.createActor("filmDetailsActor", "FilmDetailsActor");
		FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);
		Map<String,Object> createFilm = new HashMap<>();
		createFilm.put(FilmUtil.ACTION_URL,FilmUtil.ADD_FILM_URL);
		createFilm.put(FilmUtil.ACTION_PARAMETERS, film);
		Future<Object> createFilmFuture = Patterns.ask(filmDetailsActorRef, createFilm, timeout);
		Await.result(createFilmFuture, duration);
		return "success";
	}
	
	@MessageMapping("/films/{name}/comment")
	@SendTo("/topic/messages")
	public String addComment(@Payload String comment, @DestinationVariable String name) throws TimeoutException, InterruptedException {
		System.out.println("********************************"+comment +"::::::name:"+name);
		ActorRef filmDetailsActorRef = actorsProducer.createActor("filmDetailsActor", "FilmDetailsActor");
		FiniteDuration duration = FiniteDuration.create(25, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);

		Map<String,Object> saveComments = new HashMap<>();
		saveComments.put(FilmUtil.ACTION_URL,FilmUtil.ADD_COMMENTS_URL);
		saveComments.put(FilmUtil.ACTION_PARAMETERS, name+":"+comment);
		Future<Object> saveCommentsFuture = Patterns.ask(filmDetailsActorRef, saveComments, timeout);
		Await.result(saveCommentsFuture, duration);
		return "Comment added successfully! Invoke Get Film API to check the ststus.";
	}
	
	@MessageMapping("/films/{name}")
	@SendTo("/topic/messages")
	public Film getFilmByName(@PathVariable("name") String name) throws TimeoutException, InterruptedException {
		System.out.println(":::::::getFilmByName InVoked:::::::::::");
		ActorRef filmDetailsActorRef = actorsProducer.createActor("filmDetailsActor", "FilmDetailsActor");
		FiniteDuration duration = FiniteDuration.create(60, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);
		Map<String,String> action = new HashMap<>();
		action.put(FilmUtil.ACTION_URL,FilmUtil.GET_FILM_BY_NAME_URL);
		action.put(FilmUtil.ACTION_PARAMETERS, name!=null?name.trim():"");
		Future<Object> future = Patterns.ask(filmDetailsActorRef, action, timeout);
		return (Film) Await.result(future, duration);
	}
	
	@SuppressWarnings("unchecked")
	@MessageMapping("/films")
	@SendTo("/topic/messages")
	public List<Film> showAllFims() throws TimeoutException, InterruptedException {
		System.out.println("########################");
		ActorRef filmDetailsActorRef = actorsProducer.createActor("filmDetailsActor", "FilmDetailsActor");
		FiniteDuration duration = FiniteDuration.create(25, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);
		Map<String,String> action = new HashMap<>();
		action.put(FilmUtil.ACTION_URL,FilmUtil.GET_ALL_FILMS_URL);
		action.put(FilmUtil.ACTION_PARAMETERS, null);
		Future<Object> future = Patterns.ask(filmDetailsActorRef, action, timeout);
		return (List<Film>) Await.result(future, duration);
	}
	
	
	@MessageMapping("/user")
	@SendTo("/topic/messages")
	public String saveUser(@RequestBody User user) throws TimeoutException, InterruptedException {
		ActorRef filmDetailsActorRef = actorsProducer.createActor("userDetailsActor", "UserDetailsActor");
		FiniteDuration duration = FiniteDuration.create(30, TimeUnit.SECONDS);
		Timeout timeout = Timeout.durationToTimeout(duration);
		Map<String,Object> saveUser = new HashMap<>();
		saveUser.put(FilmUtil.ACTION_URL,FilmUtil.ADD_USER_URL);
		saveUser.put(FilmUtil.ACTION_PARAMETERS, user);
		Future<Object> future = Patterns.ask(filmDetailsActorRef, saveUser, timeout);
		Await.result(future, duration);
		return "success";
	}

	
	@MessageMapping("/films/greeting")
	@SendTo("/topic/messages")
    public String handle(String greeting) {
        return "Helloooooo : " + greeting;
    }

	@MessageExceptionHandler
	@SendTo("/topic/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

}
