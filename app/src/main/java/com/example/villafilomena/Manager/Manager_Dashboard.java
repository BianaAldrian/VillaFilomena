package com.example.villafilomena.Manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.villafilomena.R;

public class Manager_Dashboard extends AppCompatActivity {

    ImageView addVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_dashboard);

        addVideo = findViewById(R.id.add_homeVideo);

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}