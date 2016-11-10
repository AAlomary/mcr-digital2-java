package com.manchesterdigital.chatroom;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    String getAllMessages() throws JsonProcessingException
    {

        Map<String, Message> allMessages = new HashMap<>();
        allMessages.putAll(firebaseService.getAllMessages());

        List<Message> messageList = new ArrayList<>(allMessages.values());

        logger.debug("Returning {} messages", allMessages.size());

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonList = objectMapper.writeValueAsString(messageList);

        return jsonList;
    }

    @RequestMapping(method= RequestMethod.POST, produces = "Application/json", value="messages")
    public ResponseEntity<?> addMessage(@RequestBody String message) {

        Message message1 = null;

        try
        {
            String decodedMessage = java.net.URLDecoder.decode(message, "UTF-8");
            ObjectMapper mapper = new ObjectMapper();

            String fixMessage = decodedMessage.trim().substring(0, decodedMessage.length()-1);

             message1 = mapper.readValue(fixMessage, Message.class);

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (JsonParseException e)
        {
            e.printStackTrace();
        } catch (JsonMappingException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        String messageId = firebaseService.addMessage(message1);

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