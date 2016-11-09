package com.manchesterdigital.chatroom.vehicles;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest (webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessageControllerTest {

    public static final String ROOT_URL = "/";
    private static final String MESSAGE = "first test message";
    private static final String MESSAGES_URL = "/messages";

    @Inject
    private TestRestTemplate restTemplate;

    @Test
    public  void given_iHitRootUri_then_iGetASuccessfulFirebaseConnection()  {

        ResponseEntity<String> res = restTemplate.getForEntity(ROOT_URL, String.class);
        assertEquals(res.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void given_iAddAMessage_then_iGetAMessage() throws URISyntaxException {


        ResponseEntity<String> response = restTemplate.postForEntity(MESSAGES_URL, MESSAGE, String.class);

        // Response should be success, with location, with correct body
        assertEquals(201, response.getStatusCode().value() );
        URI location = response.getHeaders().getLocation();

        // Should be able to get all messages and see this one
        ResponseEntity<String> messages = restTemplate.getForEntity(MESSAGES_URL, String.class);
        assertEquals(HttpStatus.OK, messages.getStatusCode());
        assertTrue(messages.getBody().contains(MESSAGE));
    }

    @Test
    public void given_iAddMessage_then_messageAddedToDb () {



    }

}