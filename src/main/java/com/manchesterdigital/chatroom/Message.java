package com.manchesterdigital.chatroom;

/**
 * Created by ahmad.alomary on 09/11/2016.
 */
public class Message
{

    private String name, text, time;

    public Message(String name, String text, String time)
    {
        this.setName(name);
        this.setText(text);
        this.setTime(time);

    }

    public Message()
    {

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

    public String getTime() {
        return time;
    }

    private void setTime(String time) {
        this.time = time;
    }
}
