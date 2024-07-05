package com.example.aaochatein;

public class Messages {

    public static String SENT_BY_ME ="me";
    public static String SENT_BY_BOT="BOT";

    String mymessage;
    String sentBy;



    public Messages(String mymessage, String sentBy) {
        this.mymessage = mymessage;
        this.sentBy = sentBy;
    }

    public String getMymessage() {
        return mymessage;
    }

    public void setMymessage(String mymessage) {
        this.mymessage = mymessage;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }





}
