package com.example.villafilomena.Login_Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.villafilomena.Manager.GuestHomePage_Dashboard;
import com.example.villafilomena.Manager.Main_Dashboard;
import com.example.villafilomena.R;

public class Login_Manager extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_manager);

        login = findViewById(R.id.loginManager_btnLog);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Manager.this, Main_Dashboard.class));
            }
        });
    }
}