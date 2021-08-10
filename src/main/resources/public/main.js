// Try to set up WebSocket connection with the handshake at "http://localhost:8080/socketEndPoint"
let sock = new SockJS("http://localhost:8080/socketEndPoint");

// Create a new StompClient object with the WebSocket endpoint
let client = Stomp.over(sock);

// Start the STOMP communications, provide a callback for when the CONNECT frame arrives.
/*client.connect({}, frame => {
    // Subscribe to "/topic/messages". Whenever a message arrives add the text in a list-item element in the unordered list.
    client.subscribe("/topic/messages", payload => {
    
        let message_list = document.getElementById('message-list');
        let message = document.createElement('li');
        
        message.appendChild(document.createTextNode(JSON.parse(payload.body).message));
        message_list.appendChild(message);

    });

});*/



/*
 Same as the above example, only adding username and password headers. The rest should stay the same. 
 See "Implementing WebSockets in Spring" above for details of how the client works.
*/

client.connect({'username': 'Jimbob1', 'password': 'pass'}, (frame) => {
    alert('Call Back')
    client.subscribe("/topic/messages", payload => {
    
        let message_list = document.getElementById('message-list');
        let message = document.createElement('li');
        
        message.appendChild(document.createTextNode(JSON.parse(payload.body).message));
        
        message_list.appendChild(message);
    
    });

}); 

// Take the value in the ‘message-input’ text field and send it to the server with empty headers.
function sendMessage(){
    alert('Come to This Point')
    let input = document.getElementById("message-input");
    let message = input.value;
    let valll=JSON.stringify({message: message})
    alert(valll)
    client.send('/films/'+message, {}, valll);

}