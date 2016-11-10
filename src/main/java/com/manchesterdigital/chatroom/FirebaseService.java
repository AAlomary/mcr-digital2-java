package com.manchesterdigital.chatroom;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.inject.Named;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Named
public class FirebaseService {

    // TEMP this needs to be the actual message service
    static FirebaseDatabase database;
    Map<String, Message> messages = new HashMap<>();
    DatabaseReference ref;

    public FirebaseService() {

        try {

            fireBase();
            database = FirebaseDatabase.getInstance();
            ref = database.getReference("server/saving-data");
            ref.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot locationSnapshot : dataSnapshot.getChildren()) {
                        HashMap<String, String> obj = (HashMap)locationSnapshot.getValue();
                        Message newMessage = new Message(obj.get("name"), obj.get("text"), obj.get("time"));
                        String newId = locationSnapshot.getKey().toString();
                        messages.put(newId , newMessage);
                    }

                    ref.setValue(messages);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void fireBase() throws FileNotFoundException{

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("src/main/resources/manchesterdigitalchatroom-cfe7321d933a.json"))
                .setDatabaseUrl("https://to-do-list-cab61.firebaseio.com/")
                .build();
        FirebaseApp fba = FirebaseApp.initializeApp(options);
        fba.getOptions();
    }

    public Map<String, Message> getAllMessages() {
        return messages;
    }

    public String addMessage(Message message) {
        String messageId = UUID.randomUUID().toString();
        messages.put(messageId, message);
        ref.setValue(messages);
        return messageId;
    }

    public Message getMessage(String id) {
        return messages.get(id);
    }

}
