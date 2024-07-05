package com.example.aaochatein;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    ArrayList<MessagesModel> list ;
    Context context;
    int SENDER =1;
    int RECEIVER=2;
    FirebaseAuth auth;

    ChatAdapter(ArrayList<MessagesModel> list,Context context)
    {
        this.list=list;
        this.context=context;
        auth=FirebaseAuth.getInstance();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(context).inflate(R.layout.msg_rec,null);
        ViewHolder holder = new ViewHolder(chatview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if(list.get(position).getID().equals(auth.getUid()))
        {
            holder.receiver.setVisibility(View.GONE);
            holder.SMsg.setText(list.get(position).getMessage());
            holder.SName.setText(list.get(position).getName());
        }

        else
        {
            holder.sender.setVisibility(View.GONE);
            holder.RMsg.setText(list.get(position).getMessage());
            holder.RName.setText(list.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout sender;
        RelativeLayout receiver;
        TextView SName,SMsg,RName,RMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sender= itemView.findViewById(R.id.Senderlinear);
            receiver=itemView.findViewById(R.id.receiverlinear);
            SName=itemView.findViewById(R.id.sendername);
            RName=itemView.findViewById(R.id.receivername);
            SMsg=itemView.findViewById(R.id.sender);
            RMsg=itemView.findViewById(R.id.receiver);


        }
    }
}
