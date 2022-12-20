package com.example.villafilomena.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.villafilomena.R;

public class Main_Dashboard extends AppCompatActivity {

    ImageView guesthomepage, roomcottage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard);

        guesthomepage = findViewById(R.id.manager_GuestHomePage);
        roomcottage = findViewById(R.id.manager_RoomCottage);

        guesthomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main_Dashboard.this, GuestHomePage_Dashboard.class));
            }
        });

        roomcottage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}