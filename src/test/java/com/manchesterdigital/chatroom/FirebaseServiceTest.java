package com.manchesterdigital.chatroom;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by benyonj on 08/11/2016.
 */
public class FirebaseServiceTest {

    public final String MESSAGE = "first message";

    @Test
    public void given_iAddAMessage_then_messageSentToDb() throws FileNotFoundException {

//        FirebaseService firebaseService = new FirebaseService();
//
//        String iD = firebaseService.addMessage(MESSAGE);
//
//        Map<String, String> messages = firebaseService.getAllMessages();
//       // assertThat(firebaseService.getMessage(iD), is(MESSAGE));
//       // assertTrue(messages.containsValue(MESSAGE));
//       // assertTrue(!StringUtils.isEmpty(iD));
//     //   iD = firebaseService.addMessage("message 2");
//        assertThat(firebaseService.getAllMessages().size(), is(2));

    }

}