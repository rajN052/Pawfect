package com.example.aaochatein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.iwgang.countdownview.CountdownView;

public class  CycleActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase database;
    Users user;
    String prevmens;
    CountdownView countt;
    Button reset;
    String Id;
    Intent inten;
    AlarmManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Id = auth.getUid();
        countt = findViewById(R.id.countdown);
        long interval = getIntent().getLongExtra("interval", 0);

        manager = (AlarmManager) getSystemService(ALARM_SERVICE);
         inten  = new Intent(CycleActivity.this,BroadcasrtReceiver.class);


        Toast.makeText(CycleActivity.this, Long.toString(interval), Toast.LENGTH_SHORT).show();
        countt.start(interval);


        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                String nextcycle = DateFormat.getDateInstance().format(c.getTime());
                long currtime = c.getTime().getTime();
                c.add(Calendar.MONTH, 6);
                long nexttime = c.getTime().getTime();
                long interrval = nexttime - currtime;
                countt.start(interrval);
                setnewdate(nextcycle);
                setAlarm(interrval);

            }
        });
    }




        public void setnewdate(String date)
        {
            database.getReference().child("Users").child(Id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {
                    user =snapshot.getValue(Users.class);
                    user.setPrevmenscycle(date);

                    database.getReference().child("Users").child(Id).setValue(user);
                    database.getReference().child(user.getDogbreed()).child(Id).setValue(user);
                    database.getReference().child("ProudOwners!").child(Id).setValue(user);
                    Toast.makeText(CycleActivity.this, "info fedded", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error)
                {
                    Toast.makeText(CycleActivity.this, "please check internet", Toast.LENGTH_LONG).show();
                }
            });

    }


    public void setAlarm(long interval)
    {
        PendingIntent pintent = PendingIntent.getBroadcast(CycleActivity.this,100,inten,PendingIntent.FLAG_IMMUTABLE);
        manager.cancel(pintent);
        manager.set(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis()+interval,pintent);
    }


}







//    }
