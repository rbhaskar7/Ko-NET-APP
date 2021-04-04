package com.example.ko_net;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ko_net.databinding.ActivityAboutUsBinding;

public class AboutUs extends AppCompatActivity {

    ActivityAboutUsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        String abhirukth_description = "Abhirukth Kumar Chakravarthy\nFounder of Ko-NET\nStudent of" +
                " Indian Institute of Information Technology, Kalyani";
        String ritesh_description = "Ritesh Bhaskar\nFounder of Ko-NET\nStudent of" +
                " Indian Institute of Information Technology, Kalyani";

        binding.abhirukthDesc.setText(abhirukth_description);
        binding.riteshDesc.setText(ritesh_description);

        binding.backArr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUs.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}