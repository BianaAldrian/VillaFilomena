package com.example.villafilomena.Frontdesk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.IP_Address;
import com.example.villafilomena.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frontdesk_Booked extends AppCompatActivity {
    RecyclerView requestList;

    ArrayList<Book_Request_model> request_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontdesk_booked);

        requestList = findViewById(R.id.frontdesk_requestList);

        retrieve_BookingInfos();
    }

    private void retrieve_BookingInfos(){
        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/retrieve_bookingInfos.php";

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

                            Book_Request_model model = new Book_Request_model(object.getString("booking_id"), object.getString("currentBooking_Date"), object.getString("users_email"), object.getString("checkIn_date"),
                                    object.getString("checkIn_time"), object.getString("checkOut_date"), object.getString("checkOut_time"), object.getString("guest_count"), object.getString("room_id"), object.getString("cottage_id"),
                                    object.getString("total_cost"), object.getString("pay"), object.getString("payment_status"), object.getString("balance"), object.getString("reference_num"), object.getString("booking_status"), object.getString("invoice"));
                            request_holder.add(model);
                        }

                        Request_Adapter adapter = new Request_Adapter(request_holder);
                        requestList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

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