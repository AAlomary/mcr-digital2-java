package com.manchesterdigital.chatroom;

/**
 * Created by ahmad.alomary on 09/11/2016.
 */
public class Message
{

    private String name, text;

    public Message(String name, String text)
    {
        this.setName(name);
        this.setText(text);
    }

    public String getName()
    {
        return name;
    }

    private void setName(String name)
    {
        this.name = name;
    }

    public String getText()
    {
        return text;
    }

    private void setText(String text)
    {
        this.text = text;
    }
}
