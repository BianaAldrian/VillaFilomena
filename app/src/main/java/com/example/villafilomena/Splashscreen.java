package com.example.villafilomena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Login_Registration.Login;

public class Splashscreen extends AppCompatActivity {
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        IP = preferences.getString("IP_Address", "").trim();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!IP.equalsIgnoreCase("")){
                    String url = "http://"+IP+"/VillaFilomena/check_conn.php";

                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("true")){
                                //Toast.makeText(getApplicationContext(), "IP is correct", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }
                            else if(response.equals("false")){
                                preferences.edit().clear().commit();
                                startActivity(new Intent(getApplicationContext(), IP_Address.class));
                                Toast.makeText(getApplicationContext(),"Can't Connect to Server", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(),"Can't Connect to Server", Toast.LENGTH_LONG).show();
                                }
                            });
                    myrequest.add(stringRequest);
                }else{
                    startActivity(new Intent(getApplicationContext(), IP_Address.class));
                }
                finish();
            }
        },3000);
    }
}