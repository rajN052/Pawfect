package com.example.aaochatein;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DobbyAct extends AppCompatActivity {

    RecyclerView recycle;
    EditText send;
    ImageButton sending,sock;
    ArrayList<Messages> messages;
    ImageView kissock;
    boolean tf;
    Adapter adapter;
    MediaPlayer player;
    GenerativeModel gm ;
    GenerativeModelFutures model;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobby);
        // getSupportActionBar().hide();

        recycle= findViewById(R.id.recycle);
        send= findViewById(R.id.send);
        sending = findViewById(R.id.sendimg);
        messages = new ArrayList<>();
        kissock= findViewById(R.id.kisssock);
        sock = findViewById(R.id.sock);
        player = MediaPlayer.create(this,R.raw.dobbyisheree);

        adapter = new Adapter(messages);
        recycle.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recycle.setLayoutManager(llm);

        gm = new GenerativeModel(/* modelName */ "gemini-1.5-flash",
// Access your API key as a Build Configuration variable (see "Set up your API key" above)
                /* apiKey */"AIzaSyAd6jjawnezefQUPwOUCz_WgdqwXxiDY88" );
         model = GenerativeModelFutures.from(gm);


        sending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ques = send.getText().toString().trim();
                AddtoChat(ques,Messages.SENT_BY_ME);
                send.setText("");
                CallDobby(ques);
            }
        });



        sock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kissock.setVisibility(View.VISIBLE);
                tf=true;
                // rel.setAlpha(0);
//                if(player.isPlaying())
//                {
//                    player.stop();
//                    player.start();
//                }
//                else
                {
//                    player=MediaPlayer.create(this,R.raw.dobbyfree);
                    //  player.reset();
                    // player.setDataSource(R.raw.dobbyfree);
//                    try {
//                        player.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    recycle.setVisibility(View.INVISIBLE);
                    player.start();
                }
            }
        });



    }

    void AddtoChat(String string,String sentBy)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Messages message = new Messages(string,sentBy);
                messages.add(message);
                adapter.notifyDataSetChanged();
                recycle.scrollToPosition(adapter.getItemCount());
            }
        });

    }

    void AddResponse(String response)
    {
        AddtoChat(response,Messages.SENT_BY_BOT);
    }



    void CallDobby(String question)
    {
        // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts

        String prompt=question+"\n"+
                "answer above message like dobby from harry potter in no more than 100 words";

        Content content = new Content.Builder()
                .addText(prompt)
                .build();



                ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                System.out.println(resultText);
                AddtoChat(resultText,Messages.SENT_BY_BOT);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, this.getMainExecutor());
    }


    @Override
    public void onBackPressed() {
        if(tf)
        {
            kissock.setVisibility(View.INVISIBLE);

//
            if(player.isPlaying())
                player.stop();
            player = MediaPlayer.create(this, R.raw.dobbyisheree);
            tf=false;
            recycle.setVisibility(View.VISIBLE);
        }
        else
        {
            super.onBackPressed();
        }
    }


}