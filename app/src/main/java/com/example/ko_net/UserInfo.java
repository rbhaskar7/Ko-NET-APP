package com.example.ko_net;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ko_net.Models.Users;
import com.example.ko_net.databinding.ActivityUserInfoBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserInfo extends AppCompatActivity {

    ActivityUserInfoBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

//    private ImageView userProfilePic;
//    private TextView userUserName, userBio;
//    private DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        setContentView(binding.getRoot());

        String userName = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");
        String userBio = getIntent().getStringExtra("bio");

        binding.tvUser.setText(userName);
        binding.tvBio.setText(userBio);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_avatar).into(binding.showProfilePic);

//        String userName = getIntent().getStringExtra("userName");
//
//        userDatabase = database.getReference().child("Users").child(userName);
//
//        userProfilePic = (ImageView) findViewById(R.id.showProfilePic);
//        userUserName = (TextView) findViewById(R.id.tvUser);
//        userBio = (TextView) findViewById(R.id.tvBio);
//
//        userDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String display_name = snapshot.child("username").getValue().toString();
//                String display_bio = snapshot.child("bio").getValue().toString();
//                String display_pic = snapshot.child("profilePic").getValue().toString();
//
//                userUserName.setText(display_name);
//                userBio.setText(display_bio);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        binding.infoBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}