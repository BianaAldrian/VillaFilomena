package com.example.villafilomena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Login_Registration.Login;

public class IP_Address extends AppCompatActivity {
    EditText ip;
    Button verify;

    public static String IP_Address;
    String IP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ip_address);

        ip = findViewById(R.id.ip);
        verify = findViewById(R.id.verify);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        IP = preferences.getString("IP_Address", "");

        if(!IP.equalsIgnoreCase("")){
            String url = "http://"+IP+":8080/VillaFilomena/check_conn.php";

            RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.equals("true")){
                        //Toast.makeText(getApplicationContext(), "IP is correct", Toast.LENGTH_SHORT).show();
                        IP_Address = IP+":8080";
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                    else if(response.equals("false")){
                        preferences.edit().clear().commit();
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
        }

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String url = "http://"+ip.getText().toString()+":8080/VillaFilomena/check_conn.php";

                    RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("true")){
                                //Toast.makeText(getApplicationContext(), "IP is correct", Toast.LENGTH_SHORT).show();
                                IP_Address = ip.getText().toString()+":8080";

                                editor.putString("IP_Address",ip.getText().toString());
                                editor.apply();

                                startActivity(new Intent(getApplicationContext(), Login.class));
                                finish();
                            }
                            else if(response.equals("false")){
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
            }
        });
    }
}