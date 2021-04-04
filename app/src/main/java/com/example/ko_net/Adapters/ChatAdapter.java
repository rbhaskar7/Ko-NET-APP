package com.example.ko_net.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ko_net.Models.Message;
import com.example.ko_net.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<Message> messages;
    Context context;
    Long time_stamp;
    String receiverId;
    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<Message> messages, Context context, String receiverId) {
        this.messages = messages;
        this.context = context;
        this.receiverId = receiverId;
    }

    public ChatAdapter(ArrayList<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
            return SENDER_VIEW_TYPE;
        else
            return RECEIVER_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == SENDER_VIEW_TYPE) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender, parent, false);
            return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        Long timeStamp = messages.get(position).getTimeStamp();

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeStamp);
        String Time = DateFormat.format("HH:mm", cal).toString();

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.alert_delete, null);

                Button yes = view.findViewById(R.id.yes);
                Button no = view.findViewById(R.id.no);

                AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view).create();
                alertDialog.show();

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        String sender_Room = FirebaseAuth.getInstance().getUid() + receiverId;
                        database.getReference().child("chats").child(sender_Room).child(message.getMessageId()).setValue(null);
                        alertDialog.dismiss();
                    }
                });

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
//                new AlertDialog.Builder(context)
//                        .setTitle("DELETE?")
//                        .setMessage("Are you sure you want delete this message?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        FirebaseDatabase database = FirebaseDatabase.getInstance();
//                        String sender_Room = FirebaseAuth.getInstance().getUid() + receiverId;
//                        database.getReference().child("chats").child(sender_Room).child(message.getMessageId()).setValue(null);
//                    }
//                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();
                return false;
            }
        });
        if(holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder)holder).senderMessage.setText(message.getMessage());
            ((SenderViewHolder)holder).senderTime.setText(Time);
        }
        else {
            ((ReceiverViewHolder)holder).receiverMessage.setText(message.getMessage());
            ((ReceiverViewHolder)holder).receiverTime.setText(Time);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMessage, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverMessage = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMessage, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMessage = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }
}
