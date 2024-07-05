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
        JSONObject body = new JSONObject();
        try {
            body.put("model","text-davinci-003");
            body.put("prompt",question);
            body.put("max_tokens",4000);
            body.put("temperature",0);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        RequestBody Jbody  = RequestBody.create(body.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(Jbody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                AddResponse("Failed due to "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        AddResponse(result.trim());
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }


                }
                else
                {
                    AddResponse("Failed due to"+response.body().toString());
                }
            }
        });


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