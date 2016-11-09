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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MessageController {


    FirebaseService firebaseService;

    public MessageController(@Autowired FirebaseService firebaseService) {

        this.firebaseService = firebaseService;

    }

    private static final Logger logger = LoggerFactory
            .getLogger(MessageController.class);


    @RequestMapping(method=RequestMethod.GET, produces = "Application/json", value="messages")
    public @ResponseBody
    List<Object> getAllMessages() {

        Map<String, String> allMessages = new HashMap<>();
        allMessages.putAll(firebaseService.getAllMessages());

        List<Object> messageList = new ArrayList<Object>(allMessages.values());

        logger.debug("Returning {} messages", allMessages.size());

        return messageList;
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