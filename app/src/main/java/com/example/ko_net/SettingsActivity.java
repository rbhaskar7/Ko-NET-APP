package com.example.ko_net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ko_net.Models.Users;
import com.example.ko_net.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = binding.etUser.getText().toString();
                String bio = binding.etBio.getText().toString();

                HashMap<String, Object> obj = new HashMap<>();
                obj.put("username", userName);
                obj.put("bio", bio);

                database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).updateChildren(obj);
//                binding.etUser.setText(userName);
//                binding.etBio.setText(bio);

                Toast.makeText(SettingsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                Intent intent_save = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(intent_save);
            }
        });

        binding.aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_about_us = new Intent(SettingsActivity.this, AboutUs.class);
                startActivity(intent_about_us);
            }
        });

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.ic_avatar).into(binding.profilePic);
                binding.etBio.setText(users.getBio());
                binding.etUser.setText(users.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 33);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            Uri pic = data.getData();
            binding.profilePic.setImageURI(pic);

            final StorageReference reference = storage.getReference().child("profilePic").child(FirebaseAuth.getInstance().getUid());

            reference.putFile(pic).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profilePic").setValue(uri.toString());
                            Toast.makeText(SettingsActivity.this, "Profile Pic Updated", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}