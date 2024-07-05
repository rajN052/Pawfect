package com.example.aaochatein.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aaochatein.Models.EdMModel;
import com.example.aaochatein.R;

import java.util.ArrayList;

public class EdAdapter extends RecyclerView.Adapter<EdAdapter.viewholder>
{

    ArrayList<EdMModel> list;
    Context context;
    static MediaPlayer play;
    ArrayList<MediaPlayer> player;

    public EdAdapter(ArrayList<EdMModel> list, Context context, ArrayList<MediaPlayer> media) {
        this.list = list;
        this.context = context;
        this.player=media;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View edlayout1=LayoutInflater.from(context).inflate(R.layout.sample,parent,false);
        return new viewholder(edlayout1);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        EdMModel mode = list.get(position);
        holder.img.setImageResource(mode.getPic());
        holder.txt.setText(mode.getText());

        holder.txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play=mode.getPlay();
                if(!play.isPlaying()) {
                    for(int i=0;i<player.size();i++)
                    {
                        if(player.get(i).isPlaying() && player.get(i)!=play)
                        {
                            player.get(i).pause();
                        }
                    }
                    play.start();
                }
                else
                {
                    play.pause();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
        ImageView img;
        TextView txt;
        MediaPlayer play;
        public viewholder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.ed1);
            txt=itemView.findViewById(R.id.perfect);
        }
    }
}
