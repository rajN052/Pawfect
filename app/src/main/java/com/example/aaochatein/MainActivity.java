package com.example.aaochatein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CardView custom,common;
    TextView view;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String breed;
    Users user;
    Button cycle;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String Id= auth.getUid();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        custom=findViewById(R.id.customcommunity);

        cycle=findViewById(R.id.menstrual);

        common = findViewById(R.id.commoncommunity);



        view=findViewById(R.id.customcom);
        view.setText("Loading,please standby!");

        database.getReference().child("Users").child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                 user =snapshot.getValue(Users.class);
                 breed = user.getDogbreed()+" Community!";
                view.setText(breed);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                    view.setText("please check internet");
            }
        });


        common.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra("comname","ProudOwners!");
                startActivity(intent);
            }
        });


        custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra("comname",user.getDogbreed());
                startActivity(intent);
            }
        });



        cycle.setOnClickListener (new View.OnClickListener()  {
            @Override
            public void onClick(View v)  {

                if(user.getGender()!=null &&user.getGender().compareTo("female")==0) {
                    String prevmens = user.getPrevmenscycle();

                        Date D = getDate(prevmens);
                        Date D1=null;
                        boolean flag=false;

                        long interval = calculateInterval(D);
                        long max=15562000000L;
                        int COUNT=0;
                        while(interval<0)
                        {
                            if(COUNT>=1)
                            {
                                D=D1;
                                flag=true;
                            }
                            D1=Add6(D);


                            interval=calculateInterval(D1);
                            COUNT++;
                        }

                        if(flag)
                        {
                            String datee= DateFormat.getDateInstance().format(D);
                            user.setPrevmenscycle(datee);
                            database.getReference().child("Users").child(Id).setValue(user);
                            database.getReference().child(user.getDogbreed()).child(Id).setValue(user);
                            database.getReference().child("ProudOwners!").child(Id).setValue(user);
                            Toast.makeText(MainActivity.this, "Info Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,BroadcasrtReceiver.class);
                            PendingIntent pintent = PendingIntent.getBroadcast(MainActivity.this,100,intent,PendingIntent.FLAG_IMMUTABLE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP,Calendar.getInstance().getTimeInMillis()+interval,pintent); //changekaroo
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(D);
                    String currtime= DateFormat.getDateInstance().format(c.getTime());
                    user.setPrevmenscycle(currtime);

                    Intent intent = new Intent(MainActivity.this,CycleActivity.class);
                        intent.putExtra("interval",interval);
                    startActivity(intent);
                }

                else
                {
                    Toast.makeText(MainActivity.this, "This feature is only for female Dogs", Toast.LENGTH_LONG).show();
                }


            }
        });






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.homepage_res,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        switch(id)
        {
            case R.id.settings:
            {
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.logout:
            {
                auth.signOut();
                Intent inten = new Intent(MainActivity.this,GoogleLogin.class);
                startActivity(inten);
            }
        }
        return super.onOptionsItemSelected(item);
    }



    public Date getDate(String prevmens) {

        DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
        Date d = null;
        try {
            d = formatter.parse(prevmens);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }



    public long calculateInterval(Date d)
    {

        Date now = new Date();
        long curtime= now.getTime();
        long newtime=d.getTime();
        long interval = newtime-curtime;
        return interval;
    }

    public Date Add6(Date d)
    {
        Calendar c =Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH,6);
        d=c.getTime();
        return d;
    }


    }


