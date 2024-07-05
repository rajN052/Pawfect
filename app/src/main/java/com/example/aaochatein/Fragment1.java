package com.example.aaochatein;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment1 extends Fragment {


    ImageView community,music,bot,guides,cycle;
    CardView hachiko;
    ImageView sexydobby;
   // FirebaseAuth auth;
    TextView name,age,breed;
    FirebaseDatabase database;
    FirebaseAuth auth;
    Users user;


    public Fragment1() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_1, container, false);

        hachiko=v.findViewById(R.id.card);
        music=v.findViewById(R.id.musiccard);
        guides=v.findViewById(R.id.guidescard);
        name=v.findViewById(R.id.name);
        age=v.findViewById(R.id.age);
        breed=v.findViewById(R.id.breed);
        cycle=v.findViewById(R.id.menstrualcard);
        community=v.findViewById(R.id.community);

        //auth=FirebaseAuth.getInstance();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        String ID= auth.getUid();


        database.getReference().child("Users").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                user =snapshot.getValue(Users.class);
                name.setText(user.getDogname());
                age.setText(user.getDogAge());
                breed.setText(user.getDogbreed());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
            }
        });

//        bot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),DobbyAct.class);
//                startActivity(intent);
//            }
//        });

        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MusicAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Please navigate from menu!:)", Toast.LENGTH_SHORT).show();
            }
      });

        guides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager mng= getActivity().getSupportFragmentManager();
                FragmentTransaction t=mng.beginTransaction();
                t.replace(R.id.container,new Fragmenthome());
                t.commit();
            }
        });

        hachiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Hachisact.class);
                startActivity(intent);
            }
        });

//        sexydobby.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    Intent intent = new Intent(getActivity(),DobbyAct.class);
//                    startActivity(intent);
//                }
//        });
//
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((HomePage) getActivity()).SetNavItemChecked(0);
    }



}