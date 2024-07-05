package com.example.aaochatein;

public class MessagesModel
{
    String name,ID,message;
    long time;



    public MessagesModel(String name, String ID, String message, long time) {
        this.name = name;
        this.ID = ID;
        this.message = message;
        this.time = time;
    }

    public MessagesModel(){};


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
