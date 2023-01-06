package com.example.villafilomena.Login_Registration;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Frontdesk.Frontdesk_Dashboard;
import com.example.villafilomena.Guest.home_booking.MainFrame;
import com.example.villafilomena.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;

public class Login_Frontdesk extends AppCompatActivity {
    String IP;
    TextInputEditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_frontdesk);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        IP = preferences.getString("IP_Address", "").trim();

        login = findViewById(R.id.loginFrontdesk_btnLog);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IP.equalsIgnoreCase("")){
                    String url = "http://"+IP+"/VillaFilomena/Frontdesk_login.php";
                    //Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equals("not_exist")){
                                Toast.makeText(getApplicationContext(), "Account doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.equals("true")){

                                FirebaseMessaging.getInstance().getToken()
                                        .addOnCompleteListener(new OnCompleteListener<String>() {
                                            @Override
                                            public void onComplete(@NonNull Task<String> task) {
                                                if (!task.isSuccessful()) {
                                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                                    return;
                                                }

                                                // Get new FCM registration token
                                                String token = task.getResult();
                                                update_frontdestToken(token);
                                            }
                                        });
                                startActivity(new Intent(getApplicationContext(), Frontdesk_Dashboard.class));
                                finish();
                                //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.equals("false")){
                                Toast.makeText(getApplicationContext(),"Incorrect Password", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            })
                    {
                        @Override
                        protected HashMap<String,String> getParams() throws AuthFailureError {
                            HashMap<String,String> map = new HashMap<String,String>();
                            map.put("username",username.getText().toString());
                            map.put("password",password.getText().toString());
                            return map;
                        }
                    };
                    myrequest.add(stringRequest);
                }
            }
        });
    }

    private void update_frontdestToken(String token) {
        if (!IP.equalsIgnoreCase("")) {
            String url = "http://"+IP+"/VillaFilomena/update_frontdeskToken.php";
            RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Success")){
                        Log.w(TAG, "Token update Success");
                    }else{
                        Toast.makeText(getApplicationContext(), "Token update Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected HashMap<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("username", username.getText().toString());
                    map.put("token", token);
                    return map;
                }
            };
            myrequest.add(stringRequest);
        }
    }

}