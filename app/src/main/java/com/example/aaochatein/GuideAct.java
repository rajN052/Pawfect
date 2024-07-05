package com.example.aaochatein;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;

public class GuideAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        MediaPlayer mp;

        mp= MediaPlayer.create(GuideAct.this, Settings.System.DEFAULT_RINGTONE_URI);
       mp.setLooping(true);
       mp.start();
    }
}