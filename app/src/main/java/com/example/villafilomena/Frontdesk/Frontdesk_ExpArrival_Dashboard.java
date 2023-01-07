package com.example.villafilomena.Frontdesk;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Guest.home_booking.RoomInfos_model;
import com.example.villafilomena.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Frontdesk_ExpArrival_Dashboard extends AppCompatActivity {
    String IP;
    RecyclerView expArrival_list;

    ArrayList<Guest_arrival_departure_model> guestHolder;
    String guestname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdesk_exp_arrival_dashboard);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        IP = preferences.getString("IP_Address", "").trim();

        expArrival_list = findViewById(R.id.guest_ExpArrival_List);

        retrieveGuest_Info();
    }

    int list_no = 0;

    private void retrieveGuest_Info() {
        if (!IP.equalsIgnoreCase("")) {
            String url = "http://" + IP + "/VillaFilomena/retrieve_bookingInfos2.php";
            RequestQueue myrequest = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        guestHolder = new ArrayList<>();
                        if (success.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);

                                Guest_Name(object.getString("users_email"));

                                Guest_arrival_departure_model model = new Guest_arrival_departure_model(list_no++, object.getString("booking_id"),object.getString("currentBooking_Date"),guestname,object.getString("checkIn_date"), object.getString("checkIn_time"),
                                        object.getString("checkOut_date"),object.getString("checkOut_time"),object.getString("guest_count"),object.getString("room_id"),object.getString("cottage_id"), object.getString("total_cost"),object.getString("pay"),
                                        object.getString("payment_status"),object.getString("balance"),object.getString("reference_num"),object.getString("booking_status"),object.getString("invoice"));

                                guestHolder.add(model);

                                /*String[] roomID = object.getString("room_id").split(",");
                                for (String s : roomID) {
                                    RoomInfos(s.trim());
                                }
                                String roomSize = String.valueOf(roomID.length);*/
                            }

                            Guest_arrival_departure_Adapter adapter = new Guest_arrival_departure_Adapter(guestHolder);
                            GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                            expArrival_list.setLayoutManager(layoutManager);
                            expArrival_list.setAdapter(adapter);

                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to get", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to get guest informations", Toast.LENGTH_SHORT).show();
                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });
            myrequest.add(stringRequest);
        }
    }

    private void Guest_Name(String email){
        if(!IP.equalsIgnoreCase("")){
            String url = "http://"+IP+"/VillaFilomena/retrieve_userDetails2.php";

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

                                guestname = object.getString("fullname");
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed to get", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Failed to get room infos", Toast.LENGTH_SHORT).show();
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
                    map.put("email",email);
                    return map;
                }
            };
            myrequest.add(stringRequest);
        }
    }
}