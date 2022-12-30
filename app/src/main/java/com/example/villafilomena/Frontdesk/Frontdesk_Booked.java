package com.example.villafilomena.Frontdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Guest.home_booking.RoomInfos_adapter;
import com.example.villafilomena.Guest.home_booking.RoomInfos_model;
import com.example.villafilomena.IP_Address;
import com.example.villafilomena.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frontdesk_Booked extends AppCompatActivity {
    RecyclerView requestList;

    ArrayList<Frontdesk_userDetailsModel> request_holder;
    Request_Adapter.ClickListener clickListener;

    public static String email, fullname, address, contactNum;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdesk_booked);
        retrieve_BookingInfos();

        thread = null;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    retrieve_BookingInfos();
                }
            }
        });
        thread.start();

        requestList = findViewById(R.id.frontdesk_requestList);
        OnItemClick();

    }

    /*private void Testing(){
        Runnable objRunnable = new Runnable() {
           *//* Message objMessage = objHandler.obtainMessage();
            Bundle objBundle = new Bundle();*//*

            @Override
            public void run() {
                while(true){
                    try{
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    retrieve_BookingInfos();
                }

               *//* objBundle.putString("MSG_KEY", );

                objHandler.sendEmptyMessage(0);*//*
            }
        };

        Thread objBgThread = new Thread(objRunnable);
        objBgThread.start();

    }*/

    private void retrieve_BookingInfos(){
        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/retrieve_userDetails.php";

        RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    request_holder = new ArrayList<>();

                    if(success.equals("1")){

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Frontdesk_userDetailsModel model = new Frontdesk_userDetailsModel(object.getString("email"),object.getString("fullname"),object.getString("contact"),object.getString("address"));
                            request_holder.add(model);
                        }

                        Request_Adapter adapter = new Request_Adapter(Frontdesk_Booked.this,request_holder, clickListener);
                        GridLayoutManager layoutManager = new GridLayoutManager(Frontdesk_Booked.this, 2);
                        requestList.setLayoutManager(layoutManager);
                        requestList.setAdapter(adapter);

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

    private void OnItemClick(){
        clickListener = new Request_Adapter.ClickListener() {
            @Override
            public void onClick(View v,int position) {
                Frontdesk_userDetailsModel model = request_holder.get(position);

                startActivity(new Intent(getApplicationContext(), Frontdesk_Onlinebooking.class));

                email = model.getEmail();
                fullname = model.getFullname();
                contactNum = model.getContact();
                address = model.getAddress();
            }
        };
    }
}