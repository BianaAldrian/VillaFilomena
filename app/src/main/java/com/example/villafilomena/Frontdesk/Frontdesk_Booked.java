package com.example.villafilomena.Frontdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

    public static String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdesk_booked);

        requestList = findViewById(R.id.frontdesk_requestList);
        request_holder = new ArrayList<>();
        retrieve_BookingInfos();
        OnItemClick();

    }

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

                    if(success.equals("1")){

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            Frontdesk_userDetailsModel model = new Frontdesk_userDetailsModel(object.getString("email"),object.getString("fullname"),object.getString("contact"),object.getString("address"));
                            request_holder.add(model);
                        }

                        Request_Adapter adapter = new Request_Adapter(Frontdesk_Booked.this,request_holder, clickListener);
                        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
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
                //Toast.makeText(Frontdesk_Booked.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                Frontdesk_userDetailsModel model = request_holder.get(position);

                startActivity(new Intent(getApplicationContext(), Frontdesk_Onlinebooking.class));

                Frontdesk_Onlinebooking.email = model.getEmail();
                Frontdesk_Onlinebooking.fullname = model.getFullname();
                Frontdesk_Onlinebooking.contact = model.getContact();
                Frontdesk_Onlinebooking.address = model.getAddress();
            }
        };
    }
}