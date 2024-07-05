package com.example.aaochatein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    FirebaseDatabase database;
    FirebaseUser user;
    RecyclerView recycle;
    ImageButton send;
    EditText sent;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        String breed=getIntent().getStringExtra("comname");

        recycle=findViewById(R.id.recycle);
        send=findViewById(R.id.sendimg);
        sent=findViewById(R.id.send);
        user=FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<MessagesModel> list1= new ArrayList<>();
        ChatAdapter adapter = new ChatAdapter(list1,this);
        recycle.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recycle.setLayoutManager(llm);


        database= FirebaseDatabase.getInstance();

        database.getReference().child(breed+"chat").addValueEventListener(new ValueEventListener() {
            @Override

                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list1.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        MessagesModel model = snapshot1.getValue(MessagesModel.class);
                        //assert model != null;

                        list1.add(model);

                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = sent.getText().toString();
                MessagesModel model = new MessagesModel(user.getDisplayName(), FirebaseAuth.getInstance().getUid(),msg,new Date().getTime());
                list1.add(model);

                sent.setText("");





                database.getReference().child(breed+"chat").push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ChatActivity.this, "sent", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}