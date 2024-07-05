package com.example.aaochatein.Models;

import android.media.MediaPlayer;

public class EdMModel
{
    String text;
    int pic;
    MediaPlayer play;



    public EdMModel(int pic, String text, MediaPlayer player)
    {
        this.pic=pic;
        this.text=text;
        this.play=player;
    }

    public MediaPlayer getPlay() {
        return play;
    }

    public void setPlay(MediaPlayer play) {
        this.play = play;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public String getText() {
        return text;
    }

    public int getPic() {
        return pic;
    }
}
