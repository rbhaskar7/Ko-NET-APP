package com.example.ko_net;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ko_net.Adapters.FragmentsAdapter;
import com.example.ko_net.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent_settings);
                break;

            case R.id.delete:
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.alert_delete_user, null);
                Button yes_user = view.findViewById(R.id.yes_user);
                Button no_user = view.findViewById(R.id.no_user);

                AlertDialog alertDialog = new AlertDialog.Builder(this).setView(view).create();
                alertDialog.show();

                yes_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uId = auth.getCurrentUser().getUid().toString();
                        auth.getCurrentUser().delete();
                        database.getReference().child("Users").child(uId).removeValue();
                        Toast.makeText(MainActivity.this, "Your account has been deleted", Toast.LENGTH_SHORT).show();
                        Intent intent_delete = new Intent(MainActivity.this, SIGNUP.class);
                        startActivity(intent_delete);
                        alertDialog.dismiss();
                    }
                });

                no_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;

            case R.id.logout:
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}