package com.example.villafilomena.Frontdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.villafilomena.R;

public class Frontdesk_ExpArrival_Dashboard extends AppCompatActivity {
    RecyclerView expArrival_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdesk_exp_arrival_dashboard);

        expArrival_list = findViewById(R.id.guest_ExpArrival_List);

    }
}