package com.manchesterdigital.chatroom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {


    FirebaseService firebaseService;

    public MessageController(@Autowired FirebaseService firebaseService) {

        this.firebaseService = firebaseService;

    }

    private static final Logger logger = LoggerFactory
            .getLogger(MessageController.class);

    @RequestMapping("/")
    public String index() throws FileNotFoundException {

        firebaseService.fireBase();

        return "Greetings from Spring Boot!";
    }

    @RequestMapping(method=RequestMethod.GET, produces = "Application/json", value="messages")
    public @ResponseBody
    Map<String, String> getAllMessages() {

        Map<String, String> allMessages = new HashMap<>();
        allMessages.putAll(firebaseService.getAllMessages());

        logger.debug("Returning {} messages", allMessages.size());

        return allMessages;
    }

    @RequestMapping(method= RequestMethod.POST, produces = "Application/json", value="messages")
    public ResponseEntity<?> addMessage(@RequestBody String message) {

        String messageId = firebaseService.addMessage(message);

        // Response
        Map<String,Object> model = new HashMap<>();

        model.put("id", messageId);
        model.put("content", message);

        logger.debug("created message ID {}", messageId);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(messageId).toUri();
        return ResponseEntity.created(location).build();
    }

}