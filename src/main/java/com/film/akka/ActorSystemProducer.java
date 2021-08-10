package com.film.akka;

import akka.actor.ActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ActorSystemProducer {
    @Bean    
    public ActorSystem actorSystem() {
    	 System.out.println("::::::ActorSystemProducer:::::::::");
        return ActorSystem.create("demo-akka");
    }
}