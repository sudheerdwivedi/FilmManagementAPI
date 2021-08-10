package com.film.akka;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.film.model.User;
import com.film.service.UserService;
import com.film.util.FilmUtil;
import com.film.websocket.dtos.UserDto;

import akka.actor.UntypedAbstractActor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserDetailsActor extends UntypedAbstractActor {

    @Autowired    
    private UserService userService;

    
	@Override
	public void onReceive(Object message) {
		Map<String, Object> actionMap = (HashMap<String, Object>) message;
		System.out.println("::::OnReceive method of UserDetailsActor and parameters are:" + actionMap);
		if (FilmUtil.ADD_USER_URL.equals(actionMap.get(FilmUtil.ACTION_URL))) {
			User user = (User) actionMap.get(FilmUtil.ACTION_PARAMETERS);
			UserDto userDto = new UserDto();
			userDto.setUsername(user.getUsername());
			userDto.setPassword(user.getPassword());
			userService.saveUser(userDto);
			getSender().tell("Success", getSelf());
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    
    
}