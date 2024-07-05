package com.example.aaochatein;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.viewbinding.ViewBinding;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaochatein.databinding.ActivityDogInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dog_Info extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

   AutoCompleteTextView view,gender;
   EditText Dogname,Dogage;
   Spinner spinner;
   Button btn;
    ArrayList<String> breeds,genders;
    FirebaseDatabase database;
    String datee,ID;
    String breed,gend,dogname,dogage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_dog_info);
        view = findViewById(R.id.breedautocomplete);
        gender = findViewById(R.id.genderautocomplete);
        Dogname=findViewById(R.id.DogName);
        Dogage=findViewById(R.id.DogAge);
        ID=getIntent().getStringExtra("ID");
         breeds= new ArrayList<>();
        genders= new ArrayList<>();
        btn=findViewById(R.id.btn);
        database=FirebaseDatabase.getInstance();
        //spinner= findViewById(R.id.spinner1);



        breeds.add("Doberman");
        breeds.add("Scooby Dooby Doo");
        breeds.add("Husky");
        breeds.add("German Shephard");
        breeds.add("Golden Retriever");
        genders.add("male");
        breeds.add("Shih Tzu");
        breeds.add("Indie");
        breeds.add("Akira");
        breeds.add("Bull Dog");
        breeds.add("Beagle");
        breeds.add("Pug");
        breeds.add("Cocker Spaniel");
        genders.add("female");

        ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,breeds);
        //spinner.setAdapter(spinneradapter);
        view.setAdapter(spinneradapter);
        view.setThreshold(1);

        ArrayAdapter<String> genderadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,genders);
        gender.setAdapter(genderadapter);
        gender.setThreshold(1);
//        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view1, boolean b) {
//                if(!b) {
//                    // on focus off
//                    String str =  view.getText().toString();
//
//
//                    for(int i = 0; i < breeds.size(); i++) {
//                        String temp = breeds.get(i).toString();
//                        if(str.compareTo(temp) == 0) {
//                            return;
//                        }
//                    }
//
//                    view.setText("");
//
//                }
//            }
//        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                breed= view.getText().toString();
                gend=gender.getText().toString();
                dogname=Dogname.getText().toString();
                dogage=Dogage.getText().toString();
                int b = check(breed,gend);
                if(b==0) {


                    //Toast.makeText(Dog_Info.this, "Chalo Chalein", Toast.LENGTH_SHORT).show();



                    if(gend.compareTo("female")==0)
                    {
                        Toast.makeText(Dog_Info.this, "please enter the last menstrual date", Toast.LENGTH_SHORT).show();
                        DialogFragment datepicker = new DatePickerFragment();
                        datepicker.show(getSupportFragmentManager(),"last menstruation cycle");

                    }
                    else
                    {
                        EnterintoDB("null");
                    }
                }

                else if(b==2)
                {
                    Toast.makeText(Dog_Info.this, "please enter a valid breed", Toast.LENGTH_SHORT).show();
                    view.setText("");
                }

                else if (b==1)
                {
                    Toast.makeText(Dog_Info.this, "please enter valid sex", Toast.LENGTH_SHORT).show();
                    gender.setText("");
                }
            }
        });



    }

    public int check(String breed,String gend)
    {
        String str =  view.getText().toString();
        String str1 =  gender.getText().toString();


        for(int i = 0; i < breeds.size(); i++) {
            String temp = breeds.get(i).toString();
             if(str.compareTo(temp) == 0) {

                //Toast.makeText(Dog_Info.this, "please enter a valid breed", Toast.LENGTH_SHORT).show();

                for(int j=0;j<genders.size();j++)
                {
                  String sex= genders.get(j).toString();
                  if(str1.compareTo(sex)==0)
                  {
                      return 0;
                  }
                }
                return 1;
            }

        }
        return 2;

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
       // c.set(Calendar.MONTH,6);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currtime= DateFormat.getDateInstance().format(c.getTime());


        Calendar cal = Calendar.getInstance();
        if(cal.compareTo(c)>0)
        {
            long Date_to_set=calculateInterval(Add6(c.getTime()));
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(Dog_Info.this,BroadcasrtReceiver.class);
           // Toast.makeText(this, Long.toString(Date_to_set), Toast.LENGTH_SHORT).show();
            PendingIntent pintent = PendingIntent.getBroadcast(Dog_Info.this,100,intent,PendingIntent.FLAG_IMMUTABLE);
            if(Date_to_set>0)
                {alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis()+Date_to_set,pintent);}
            datee=currtime;
            EnterintoDB(datee);
        }
        else
        {
            Toast.makeText(this, "please enter valid date", Toast.LENGTH_SHORT).show();
        }



    }


    public void EnterintoDB(String datee)
    {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Users user1= new Users(user.getDisplayName(),user.getPhoneNumber(),user.getEmail(),user.getPhotoUrl().toString(),ID);
        user1.setDogbreed(breed);
        user1.setGender(gend);
        user1.setDogAge(dogage);
        user1.setDogname(dogname);
        user1.setPrevmenscycle(datee);
        database.getReference().child("Users").child(ID).setValue(user1);
        database.getReference().child(breed).child(ID).setValue(user1);
        database.getReference().child("ProudOwners!").child(ID).setValue(user1);

        Intent intent = new Intent(Dog_Info.this,HomePage.class);//laffda
        intent.putExtra("breed",breed+" Community!");
        intent.putExtra("name",Dogname.toString());
        intent.putExtra("age",Dogage.toString());
        startActivity(intent);
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