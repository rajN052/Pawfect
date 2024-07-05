package com.example.aaochatein;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>
{
    ArrayList<Messages> msg;
    //   String kiska;
    Adapter(ArrayList<Messages> list1)
    {
        msg=list1;
        //   kiska= who;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages,null);
        ViewHolder holder = new ViewHolder(chatview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Messages message = msg.get(position);
        if(message.getSentBy().equals(message.SENT_BY_ME))
        {
            holder.bots.setVisibility(View.GONE);
            holder.mine.setVisibility(View.VISIBLE);
            holder.mines.setText(message.getMymessage());
        }
        else
        {
            holder.mine.setVisibility(View.GONE);
            holder.bots.setVisibility(View.VISIBLE);
            holder.botss.setText(message.getMymessage());
        }

    }

    @Override
    public int getItemCount() {
        return msg.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mine ;
        RelativeLayout bots;
        TextView mines,botss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mine = itemView.findViewById(R.id.Senderlinear);
            bots= itemView.findViewById(R.id.receiverlinear);
            mines=itemView.findViewById(R.id.sender);
            botss=itemView.findViewById(R.id.receiver);

        }
    }


}
