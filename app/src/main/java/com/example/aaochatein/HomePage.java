package com.example.aaochatein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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

public class HomePage extends AppCompatActivity {

    //DrawerLayout D1;

    DrawerLayout D1;
    NavigationView N1;
    Toolbar T1;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String breed;
    Users user;
    BottomNavigationView bnv;
    FloatingActionButton dobby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


       // getSupportActionBar().hide();
        N1= findViewById(R.id.navigator);
        D1=findViewById(R.id.drawer);
        T1=findViewById(R.id.toolbar);
        setSupportActionBar(T1);
//        getSupportActionBar().setDisplayShowTitleEnabled(true);
//        T1.setTitle("");
//        T1.setSubtitle("");
//        setSupportActionBar(T1);
        dobby=findViewById(R.id.calldobby);


        bnv=findViewById(R.id.navigate);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        String Id= auth.getUid();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        String breed = getIntent().getStringExtra("breed");



        ActionBarDrawerToggle toggleit = new ActionBarDrawerToggle(this,D1,T1,R.string.opendrawer,R.string.closedrawer);

        D1.addDrawerListener(toggleit);

        toggleit.syncState();

        FragmentManager mr = getSupportFragmentManager();
        FragmentTransaction tr = mr.beginTransaction();
        tr.add(R.id.container,new Fragment1());
        tr.commit();

        database.getReference().child("Users").child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                user =snapshot.getValue(Users.class);
//                breed = user.getDogbreed()+" Community!";
//                view.setText(breed);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
//                view.setText("please check internet");
                Toast.makeText(HomePage.this, "Please check Internet", Toast.LENGTH_SHORT).show();
            }
        });


        N1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();


                 if(id==R.id.Homee)
                {
//                    FragmentManager mr = getSupportFragmentManager();
//                    FragmentTransaction tr = mr.beginTransaction();
//                    tr.replace(R.id.container,new Fragment1());
//                    tr.commit();
                }
                else if(id==R.id.musicc)
                {
                    Intent inten = new Intent(HomePage.this,MusicAct.class);
                    // intent.putExtra("breed",breed);
                    startActivity(inten);
                }

                else
                {
//                    FragmentManager mr = getSupportFragmentManager();
//                    FragmentTransaction tr = mr.beginTransaction();
//                    tr.replace(R.id.container,new Fragment3());
//                    tr.commit();

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
                            Toast.makeText(HomePage.this, "Info Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomePage.this,BroadcasrtReceiver.class);
                            PendingIntent pintent = PendingIntent.getBroadcast(HomePage.this,100,intent,PendingIntent.FLAG_IMMUTABLE);
                            alarmManager.set(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis()+interval,pintent); //changekaroo
                        }
                        Calendar c = Calendar.getInstance();
                        c.setTime(D);
                        String currtime= DateFormat.getDateInstance().format(c.getTime());
                        user.setPrevmenscycle(currtime);

                        Intent intent = new Intent(HomePage.this,CycleActivity.class);
                        intent.putExtra("interval",interval);
                        startActivity(intent);
                    }

                    else
                    {
                        Toast.makeText(HomePage.this, "This feature is only for female Dogs", Toast.LENGTH_LONG).show();
                    }


                }


                D1.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id= item.getItemId();

                if(id==R.id.nav_home)
                {
                    FragmentManager mng= getSupportFragmentManager();
                    FragmentTransaction t=mng.beginTransaction();
                    t.replace(R.id.container,new Fragment1());
                    t.commit();


                }

                else if(id==R.id.nav_guides)
                {
                    FragmentManager mng= getSupportFragmentManager();
                    FragmentTransaction t=mng.beginTransaction();
                    t.replace(R.id.container,new Fragmenthome());
                    t.commit();
                }

                else
                {
//                    FragmentManager mng= getSupportFragmentManager();
//                    FragmentTransaction t=mng.beginTransaction();
//                    t.replace(R.id.container,new Fragment3());
//                    t.commit();
                    Intent intent = new Intent(HomePage.this,MainActivity.class);
                    intent.putExtra("breed",breed);
                    startActivity(intent);
                }



                return true;
            }
        });

        dobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(HomePage.this,DobbyAct.class);
                startActivity(intent);

            }
        });




    }

    boolean doubleBackToExitPressedOnce=false;

    @Override
    public void onBackPressed() {


        if(D1.isDrawerOpen(GravityCompat.START))
        {
            D1.closeDrawer(GravityCompat.START);
        }
        else {
            if (doubleBackToExitPressedOnce) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            },2000);
        }
    }

//    boolean doubleBackToExitPressedOnce=false;
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            Intent a = new Intent(Intent.ACTION_MAIN);
//            a.addCategory(Intent.CATEGORY_HOME);
//            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(a);
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
//    }

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
                Toast.makeText(this, "settings", Toast.LENGTH_LONG).show();
                break;
            }

            case R.id.logout:
            {
                auth.signOut();
                Intent inten = new Intent(HomePage.this,GoogleLogin.class);
                startActivity(inten);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void SetNavItemChecked(int id)
    {
        Menu m=bnv.getMenu();
        MenuItem mi=m.getItem(id);
        mi.setChecked(true);
        MenuItem mv;

    }



}
