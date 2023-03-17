package com.example.villafilomena.Guest.home_booking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.Frontdesk.Frontdesk_Booked;
import com.example.villafilomena.Login_Registration.Login_Guest;
import com.example.villafilomena.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Guest_Booking_Hstry extends AppCompatActivity {
    String IP;
    RecyclerView BookingHstry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_booking_hstry);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        IP = preferences.getString("IP_Address", "").trim();

        RetrieveBookingInfo();

    }

    private void RetrieveBookingInfo(){
        if(!IP.equalsIgnoreCase("")){
            String url = "http://"+IP+"/VillaFilomena/retrieve_bookingInfos.php";
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

                               /* current_date.setText(object.getString("currentBooking_Date"));
                                checkIn_DATE.setText(object.getString("checkIn_date"));
                                checIn_TIME.setText(object.getString("checkIn_time"));
                                checkOut_DATE.setText(object.getString("checkOut_date"));
                                checkOut_TIME.setText(object.getString("checkOut_time"));
                                guest_qty.setText(object.getString("guest_count"));
                                payment_balance.setText(object.getString("balance"));
                                paymentStat.setText(object.getString("payment_status"));
                                referenceNum.setText(object.getString("reference_num"));
                                total.setText(object.getString("total_cost"));

                                String[] roomID = object.getString("room_id").split(",");
                                for (String s : roomID) {
                                    RoomInfos(s.trim());
                                }
                                String roomSize = String.valueOf(roomID.length);
                                room_qty.setText(roomSize);

                                checkIn_date = object.getString("checkIn_date");
                                checkIn_time = object.getString("checkIn_time");
                                checkOut_date = object.getString("checkOut_date");
                                checkOut_time = object.getString("checkOut_time");

                                booking_id = object.getString("booking_id");
                                cottage_id = object.getString("cottage_id");

                                pay = object.getString("pay");*/
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Failed to get", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Failed to get guest informations", Toast.LENGTH_SHORT).show();
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
                    map.put("email", Login_Guest.user_email);

                    return map;
                }
            };
            myrequest.add(stringRequest);
        }
    }
}