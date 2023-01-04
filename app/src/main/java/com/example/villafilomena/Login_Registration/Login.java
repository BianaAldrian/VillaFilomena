package com.example.villafilomena.Login_Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.villafilomena.MyFirebaseMessagingService;
import com.example.villafilomena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class Login extends AppCompatActivity {

    Button guest, frondesk, manager;

    private static String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        guest = findViewById(R.id.guest);
        frondesk = findViewById(R.id.frontdesk);
        manager = findViewById(R.id.manager);


        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login_Guest.class));
            }
        });

        frondesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login_Frontdesk.class));
            }
        });

        manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login_Manager.class));
            }
        });
    }
}