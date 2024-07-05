package com.example.aaochatein;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.media.MediaPlayer;
import android.os.Bundle;
import com.example.aaochatein.Adapters.EdAdapter;
import com.example.aaochatein.Models.EdMModel;

import java.util.ArrayList;

public class MusicAct extends AppCompatActivity {

    RecyclerView recyclerView;
    private static MediaPlayer player=null;
    private static ArrayList<MediaPlayer> arr;
    private static EdAdapter adapter,adapter1;
    private static ArrayList<EdMModel> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        //getSupportActionBar().hide();
        recyclerView = findViewById(R.id.recycle);

        if(player==null) {
            player = new MediaPlayer();
            arr = new ArrayList<>();
            arr.add(MediaPlayer.create(this, R.raw.perfect));
            arr.add(MediaPlayer.create(this, R.raw.thinkingoutloud));
            arr.add(MediaPlayer.create(this, R.raw.happier));
            arr.add(MediaPlayer.create(this, R.raw.theateam));
            arr.add(MediaPlayer.create(this, R.raw.supermarketflowers));
        }


            list1 = new ArrayList<>();
            list1.add(new EdMModel(R.drawable.ed1, "Perfect", arr.get(0)));
            list1.add(new EdMModel(R.drawable.ed2, "Thinking out loud", arr.get(1)));
            list1.add(new EdMModel(R.drawable.ed3, "Happier", arr.get(2)));
            list1.add(new EdMModel(R.drawable.ed4, "A team", arr.get(3)));
            list1.add(new EdMModel(R.drawable.ed5, "Supermarket Flowers", arr.get(4)));

            adapter1 = new EdAdapter(list1, this, arr);

        adapter=adapter1;

        recyclerView.setAdapter(adapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

//        GridLayoutManager grid = new GridLayoutManager(this , 3);
//        recyclerView.setLayoutManager(grid);
//
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(manager);

    }


}