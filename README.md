# FilmManagementAPI
Steps I have followed to Implement Websocket  API to manage films

# Technology Used in the implementation
   a. SpringBoot Framework
   b. Spring Security
   c. AKKA API 
   d. WebSocket API of SpringBoot framework
   e. Spring Data JPA
   f. MS Sql Server
   
# Followed Aproach 
1. Created  four tables as below and insert the dummy data in these table through insert sql script. 
   FILM
   FILM_COMMENTS
   FILM_GENERE
   USER_DETAILS
   Insert 3 dummy records for films which having some comments and genere
2. Created Spring boot project and created the API's as below
   a. /films to show all films 
   b. /films/{name}/comment to add/update comment
   c. /films/create to add new films
   d. /films/{name} get film by its name
   e. /user to ceare new user
   With the help of spring data jpa i have stored/pull the records through/from database.
 3. Introduced akka api using SpringBoot. When the app starts up, it creates an ActorSystem with FilmDetailsActor, UserDetailsActor Actor beans and binds the defined routes 
    to a port, in this case, localhost:8080. When the endpoints are invoked, they interact with the FilmDetailsActor and UserDetailsActor Actor, which contains the business
    logic. For all Film related logic i have written in FilmDetailsActor class and for user related information i have written in UserDetailsActor claas. From these actor
    classes the business logic apis has been invoked.
    So all requests are being served through akka Actor Model, when the HTTP server receives the requests for the defined api's endpoints, it then routs to the appropriate actor 
    (UserDetailsActor and FilmDetailsActor),  actor invoke the appropriate businedd logic and returns the response to target object.
 4. Used the Spring Boot WebSocket API, i have registerd the end point with webSocket and enable the broker for pub-sub model. I have given the location to publish the business
    logic data in a particular topic/queue and WenSocket client will subscribe all te publish messages from the topic/queue. I have used SockJS library to a call to the 
    websocket enpoints. It opens two-way communication between client and server. applications using STOMP as an application-level protocol, SockJS for fallback options, and 
    a message broker for broadcasting messages to connected users.
    
    Spring Framework provides STOMP support, With some configuration, i have enabled it to act as a lightweight message broker to web clients. It will automatically handle
    subscriptions without any server code and allow controller methods to handle incoming messages and subscriptions. This is similar to how Spring MVC maps HTTP requests to
    controller methods. In fact, a Spring MVC controller can be extended to also receive STOMP over WebSocket messages. I have used the annotation in our controller class for
    esample @MessageMapping(value = "/films/create") and @SendTo("/topic/messages") for pub sub model.
    
 5. I have written one index.html page from where we are seding/receiving the request/response to/from defined webscokket end points. As we have already registed the custom end
     points with webScoket configuration area. We are subscribibg the messages from defined topic/queue for requested business data. In index.html i have set up WebSocket
     connection with the handshake at "http://localhost:8080/socketEndPoint and created a new StompClient object with the WebSocket endpoint . With the help of StompClient i 
     am sending and subscribing the messages from websocket. 
 6.  For authentication purpose i have used SpringSecurity. I have created WebSocketAuthenticatorService service for this only authentication user can access the API. I have
     registered the AuthChannelInterceptor with WebSocketMessageBrokerConfigurer, this inceptor will authenticate the user whi is going to access the business through the
     WebSocket. In AuthChannelInterceptor implementation the provided user credentials is fetched from STOMP headers and retrive the username and password. Then request goes to
     the WebSocketAuthenticatorService where we verify the user with databse. If user is authenticated user the he can access the API.
     
 
     
    
  
    

   
   
