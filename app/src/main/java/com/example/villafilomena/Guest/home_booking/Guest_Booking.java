package com.example.villafilomena.Guest.home_booking;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Guest_Booking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Guest_Booking extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Guest_Booking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frontdesk_Booking.
     */
    // TODO: Rename and change types and number of parameters
    public static Guest_Booking newInstance(String param1, String param2) {
        Guest_Booking fragment = new Guest_Booking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LinearLayout checkIn_checkOut, adult_child;
    Button reserve_continue, calendar_ok, guest_done;
    TextView kidDec, kidQty, kidInc, adultDec, adultQty, adultInc;
    int kidqty = 0, adultqty = 0;
    //entrance fee details
    TextView daytourTime, nightTourTime, daytourFee, nighttourFee;

    RecyclerView RoomInfo_list;
    ArrayList<RoomInfos_model> roominfo_holder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.guest_booking, container, false);

        checkIn_checkOut = view.findViewById(R.id.checkIn_checkOut);
        reserve_continue = view.findViewById(R.id.reserve_continue);
        adult_child = view.findViewById(R.id.adult_child);
        daytourTime = view.findViewById(R.id.guestBooking_daytour_Time);
        nightTourTime = view.findViewById(R.id.guestBooking_nighttour_Time);
        daytourFee = view.findViewById(R.id.guestBooking_daytour_Fee);
        nighttourFee = view.findViewById(R.id.guestBooking_nighttour_Fee);
        RoomInfo_list = view.findViewById(R.id.guestBooking_RoomInfos_List);

        EntranceFee_Details();

        checkIn_checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), Calendar.class));

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.guest_calendar, null);

                calendar_ok = view1.findViewById(R.id.calendar_ok);

                builder.setView(view1);
                final AlertDialog dialog = builder.create();
                dialog.show();

                calendar_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.hide();
                    }
                });
            }
        });

        adult_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.guest_quantity, null);

                kidDec = view1.findViewById(R.id.kidDec);
                kidQty = view1.findViewById(R.id.kidQty);
                kidInc = view1.findViewById(R.id.kidInc);
                adultDec = view1.findViewById(R.id.adultDec);
                adultQty = view1.findViewById(R.id.adultQty);
                adultInc = view1.findViewById(R.id.adultInc);
                guest_done = view1.findViewById(R.id.guest_done);

                builder.setView(view1);
                final AlertDialog dialog = builder.create();
                dialog.show();

                kidDec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(kidqty != 0){
                            kidqty--;
                            kidQty.setText(""+kidqty);
                        }
                    }
                });
                kidInc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        kidqty++;
                        kidQty.setText(""+kidqty);
                    }
                });

                adultDec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(kidqty != 0){
                            adultqty--;
                            adultQty.setText(""+adultqty);
                        }
                    }
                });
                adultInc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adultqty++;
                        adultQty.setText(""+adultqty);
                    }
                });

                guest_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.hide();
                    }
                });
            }
        });

        Room_Infos();

        return view;
    }

    private void EntranceFee_Details(){
        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/retrieve_EntranceFee_Details.php";

        RequestQueue myrequest = Volley.newRequestQueue(getActivity());
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

                            String day_tour = object.getString("day_tour");
                            String daytour_kid_age = object.getString("daytour_kid_age");
                            String daytour_kid_fee = object.getString("daytour_kid_fee");
                            String daytour_adult_age = object.getString("daytour_adult_age");
                            String daytour_adult_fee = object.getString("daytour_adult_fee");
                            String night_tour = object.getString("night_tour");
                            String nighttour_kid_age = object.getString("nighttour_kid_age");
                            String nighttour_kid_fee = object.getString("nighttour_kid_fee");
                            String nighttour_adult_age = object.getString("nighttour_adult_age");
                            String nighttour_adult_fee = object.getString("nighttour_adult_fee");

                            daytourTime.setText(day_tour);
                            daytourFee.setText("KID"+daytour_kid_age+" - "+daytour_kid_fee+"\n"+"ADULT"+daytour_adult_age+" - "+daytour_adult_fee);
                            nightTourTime.setText(night_tour);
                            nighttourFee.setText("KID"+nighttour_kid_age+" - "+nighttour_kid_fee+"\n"+"ADULT"+nighttour_adult_age+" - "+nighttour_adult_fee);

                        }
                    }else{
                        Toast.makeText(getActivity(), "Failed to get", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        myrequest.add(stringRequest);
    }

    private void Room_Infos(){
        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/retrieve_Room_Details.php";

        RequestQueue myrequest = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if(success.equals("1")){
                        roominfo_holder = new ArrayList<>();

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            RoomInfos_model model = new RoomInfos_model(object.getString("id"), object.getString("imageUrl"), object.getString("name"), object.getString("room_capacity"), object.getString("room_rate"));
                            roominfo_holder.add(model);
                        }

                        RoomInfos_adapter adapter = new RoomInfos_adapter(roominfo_holder);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        RoomInfo_list.setLayoutManager(layoutManager);
                        RoomInfo_list.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{
                        Toast.makeText(getActivity(), "Failed to get", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        myrequest.add(stringRequest);
    }
}