package com.example.villafilomena.Manager;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
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
import com.example.villafilomena.Frontdesk.Frontdesk_Booked;
import com.example.villafilomena.Frontdesk.Frontdesk_userDetailsModel;
import com.example.villafilomena.Frontdesk.Request_Adapter;
import com.example.villafilomena.Guest.home_booking.RoomInfos_model;
import com.example.villafilomena.Login_Registration.Login_Guest;
import com.example.villafilomena.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class FrontdeskClerks_Dashboard extends AppCompatActivity {
    Context context = this;
    String IP;

    RecyclerView clerks;
    Button addClerks;

    EditText clerkname, clerkusername, clerkpassword;

    ArrayList<Frontdesk_Clerk_model> clerk_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdesk_clerks_dashboard);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        IP = preferences.getString("IP_Address", "").trim();

        clerks = findViewById(R.id.frontdeskClerks);
        addClerks = findViewById(R.id.addClerk);

        addClerks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_frontdesk_clerks);

                clerkname = dialog.findViewById(R.id.cleark_name);
                clerkusername = dialog.findViewById(R.id.clerk_username);
                clerkpassword = dialog.findViewById(R.id.clerk_password);

                Button clerk_btnDone = dialog.findViewById(R.id.clerk_btnDone);
                clerk_btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertFrontdesk_Clerk();
                        dialog.hide();
                    }
                });

                dialog.show();
            }
        });
    }

    private void insertFrontdesk_Clerk(){
        if (!IP.equalsIgnoreCase("")) {
            String url = "http://"+IP+"/VillaFilomena/insertFrontdesk_Clerk.php";
            RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Success")){
                        retrieveFrontdesk_Clerk();
                    }else{
                        Toast.makeText(context, "Creating Front Desk Clerk Failed", Toast.LENGTH_SHORT).show();
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
                    map.put("name", clerkname.getText().toString());
                    map.put("username", clerkusername.getText().toString());
                    map.put("password", clerkpassword.getText().toString());
                    return map;
                }
            };
            myrequest.add(stringRequest);
        }
    }

    private void retrieveFrontdesk_Clerk(){
        if(!IP.equalsIgnoreCase("")){
            String url = "http://"+IP+"/VillaFilomena/retrieveFrontdesk_Clerk.php";
            RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        if(success.equals("1")){
                            clerk_holder = new ArrayList<>();
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                Frontdesk_Clerk_model model = new Frontdesk_Clerk_model(object.getString("id"),object.getString("name"),object.getString("username"),object.getString("password"),object.getString("token"));
                                clerk_holder.add(model);
                            }
                            Frontdesk_Clerk_adapter adapter = new Frontdesk_Clerk_adapter(clerk_holder);
                            GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
                            clerks.setLayoutManager(layoutManager);
                            clerks.setAdapter(adapter);

                        }else{
                            Toast.makeText(getApplicationContext(), "Failed to get", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            myrequest.add(stringRequest);
        }
    }
}