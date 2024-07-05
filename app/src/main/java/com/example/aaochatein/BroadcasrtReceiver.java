package com.example.aaochatein;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

import java.net.URI;

public class BroadcasrtReceiver extends BroadcastReceiver {

    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent)
    {
//        mp=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
//        mp.setLooping(true);
//        mp.start();
        Intent alarmintent = new Intent(context,GuideAct.class);
        alarmintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alarmintent);

    }
}
