package com.film.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ActorsProducer {
    @Autowired
    private volatile ApplicationContext applicationContext;

    @Autowired
    private ActorSystem actorSystem;

    public ActorRef createActor(String actorBeanName, String actorName) {
        Props props = Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
        System.out.println("::::::::ActorsProducer:::ActorName:"+actorName);
        return actorSystem.actorOf(props);
    }
}