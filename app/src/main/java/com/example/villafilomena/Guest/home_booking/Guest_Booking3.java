package com.example.villafilomena.Guest.home_booking;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.villafilomena.IP_Address;
import com.example.villafilomena.Login_Registration.Login_Guest;
import com.example.villafilomena.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringJoiner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Guest_Booking3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Guest_Booking3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Guest_Booking3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Guest_Booking3.
     */
    // TODO: Rename and change types and number of parameters
    public static Guest_Booking3 newInstance(String param1, String param2) {
        Guest_Booking3 fragment = new Guest_Booking3();
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

    Thread thread;
    boolean booking_stat = false;

    TextView details, waiting_confirmation, txtBooking_confirmed, txtInvoice_Link;
    RadioButton Percent50, Percent100;
    EditText reference;
    String checkIn_Day,checkOut_Day,checkIn_Month,checkOut_Month,checkIn_Year,checkOut_Year,checkIn_Time,checkOut_Time;

    ArrayList<String> room_id = new ArrayList<>();
    ArrayList<Double> room_rate = new ArrayList<>();

    int kidCount, adultCount;
    int numofDays, numofNights;
    double KidFee_Day, KidFee_Night, AdultFee_Day, AdultFee_Night;
    double totalFee,payment=0, balance=0;
    String percent, InvoiceUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.guest_booking3, container, false);

        details = view.findViewById(R.id.guestBooking3_txtDetails);
        waiting_confirmation = view.findViewById(R.id.txtBooking_pending);
        txtBooking_confirmed =view.findViewById(R.id.txtBooking_confirmed);
        txtInvoice_Link = view.findViewById(R.id.txt_InvoiceLink);
        Percent50 = view.findViewById(R.id.guestBooking3_Rbtn50);
        Percent100 = view.findViewById(R.id.guestBooking3_Rbtn100);
        reference = view.findViewById(R.id.guestBooking3_referenceNum);

        txtInvoice_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference invoiceReference = FirebaseStorage.getInstance().getReferenceFromUrl(InvoiceUrl);
                String InvoiceName = invoiceReference.getName();

                String url = InvoiceUrl;

                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.setTitle("Invoice");
                request.setDescription("Downloading file...");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, InvoiceName);

                DownloadManager manager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }
        });

        checkIn_Day = Guest_Booking.checkInOut_day[0];
        checkOut_Day = Guest_Booking.checkInOut_day[1];
        checkIn_Month = Guest_Booking.checkInOut_month[0];
        checkOut_Month = Guest_Booking.checkInOut_month[1];
        checkIn_Year = Guest_Booking.checkInOut_year[0];
        checkOut_Year = Guest_Booking.checkInOut_year[1];
        checkIn_Time = Guest_Booking.checkIn_Time[0];
        checkOut_Time = Guest_Booking.checkOut_Time[0];
        kidCount = Guest_Booking.kidqty;
        adultCount = Guest_Booking.adultqty;
        KidFee_Day = Guest_Booking.kidFee_Day;
        KidFee_Night = Guest_Booking.kidFee_Night;
        AdultFee_Day = Guest_Booking.adultFee_Day;
        AdultFee_Night = Guest_Booking.adultFee_Night;

        if(checkIn_Time == "Day Tour" && checkOut_Time == "Day Tour"){
            numofDays = Guest_Booking.numDays + 1;
            numofNights = Guest_Booking.numNights;
        }
        else if(checkIn_Time == "Night Tour" && checkOut_Time == "Night Tour"){
            numofNights = Guest_Booking.numNights + 1;
            numofDays = Guest_Booking.numDays;
        }
        else if(checkIn_Time == "Day Tour" && checkOut_Time == "Night Tour"){
            numofDays = Guest_Booking.numDays + 1;
            numofNights = Guest_Booking.numNights + 1;
        }
        else if(checkIn_Time == "Night Tour" && checkOut_Time == "Day Tour"){
            numofDays = Guest_Booking.numDays + 1;
            numofNights = Guest_Booking.numNights + 1;
        }

        StringJoiner checkIn_checkOut = new StringJoiner("");

        checkIn_checkOut.add("Check-In "+checkIn_Day+"/"+checkIn_Month+"/"+checkIn_Year+" - "+ checkIn_Time +
                "\n"+"Check-Out "+checkOut_Day+"/"+checkOut_Month+"/"+checkOut_Year+" - "+ checkOut_Time);

        StringJoiner roomInfos = new StringJoiner("\n");
        if(!Guest_Booking2.roominfo_holder.isEmpty()){
            for(int i=0;i<Guest_Booking2.roominfo_holder.size();i++){
                RoomInfos_model model = Guest_Booking2.roominfo_holder.get(i);
                roomInfos.add(model.getName()+" - "+model.getRoom_rate());

                room_id.add(model.getId());
                room_rate.add(Double.valueOf(model.getRoom_rate()));
            }
        }else{
            room_id.add(String.valueOf(0));
            room_rate.add(Double.valueOf(0));
        }


        double ttlKidFee = ((KidFee_Day * kidCount) * numofDays) + ((KidFee_Night * kidCount) * numofNights);
        double ttlAdultFee = ((AdultFee_Day * adultCount) * numofDays) + ((AdultFee_Night * adultCount) * numofNights);

        double roomFee = 0;
        for (int i=0; i<room_rate.size(); i++){
            roomFee += room_rate.get(i);
        }

        totalFee = ttlKidFee + ttlAdultFee + roomFee;

        details.setText(checkIn_checkOut+"\n"+
                kidCount+" Kid/s and "+adultCount+" Adult/s"+"\n"+
                roomInfos+"\n"+
                totalFee);

        room_id.toString().replace("[","").replace("]","");

        Percent50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percent = "50";
                payment = totalFee * 0.5;
                balance = totalFee - payment;

                details.setText(checkIn_checkOut+"\n"+
                        kidCount+" Kid/s and "+adultCount+" Adult/s"+"\n"+
                        roomInfos+"\n"+
                        totalFee+"\n"+
                        "Bill: "+payment);
            }
        });
        Percent100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                percent = "100";
                payment = totalFee * 1;
                balance = totalFee - payment;

                details.setText(checkIn_checkOut+"\n"+
                        kidCount+" Kid/s and "+adultCount+" Adult/s"+"\n"+
                        roomInfos+"\n"+
                        totalFee+"\n"+
                        "Bill: "+payment);
            }
        });

        MainFrame.Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBooking_informatiion();
                waiting_confirmation.setVisibility(View.VISIBLE);

                thread = null;
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!booking_stat){
                            try {
                                Thread.sleep(1000);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            Check_Status();
                        }
                    }
                });
                thread.start();
            }
        });

        return view;
    }

    private void Check_Status(){
        String url = "http://"+IP_Address.IP_Address+"/VillaFilomena/retrieve_bookingInfos.php";

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

                            if(object.getString("reference_num").equals(reference.getText().toString())){
                                InvoiceUrl = object.getString("invoice");

                                if (object.getString("booking_status").equals("Confirmed")){
                                    booking_stat = true;
                                    thread.interrupt();
                                    waiting_confirmation.setVisibility(View.GONE);
                                    txtBooking_confirmed.setVisibility(View.VISIBLE);
                                    txtInvoice_Link.setVisibility(View.VISIBLE);
                                }else {
                                    booking_stat = false;
                                }
                            }
                        }
                    }else{
                        Toast.makeText(getActivity(), "Failed to get", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Failed to get guest informations", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
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

    private void insertBooking_informatiion(){
        String url = "http://"+ IP_Address.IP_Address+"/VillaFilomena/insert_bookingInfos.php";
        RequestQueue myrequest = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")){
                    Toast.makeText(getActivity(), "Please wait for confirmation", Toast.LENGTH_SHORT).show();
                }
                else if(response.equals("Failed")){
                    Toast.makeText(getActivity(), "Unexpected Error, Please try again! ", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected HashMap<String,String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("users_email", Login_Guest.user_email);
                map.put("checkIn_date",checkIn_Day+"/"+checkIn_Month+"/"+checkIn_Year+"/");
                map.put("checkIn_time",checkIn_Time);
                map.put("checkOut_date",checkOut_Day+"/"+checkOut_Month+"/"+checkOut_Year);
                map.put("checkOut_time",checkOut_Time);
                map.put("guest_count", String.valueOf(kidCount+adultCount));
                map.put("room_id",room_id.toString().replace("[","").replace("]",""));
                map.put("cottage_id","null");
                map.put("total_cost", String.valueOf(totalFee));
                map.put("pay", String.valueOf(payment));
                map.put("payment_status",percent);
                map.put("balance", String.valueOf(balance));
                map.put("reference_num",reference.getText().toString());
                map.put("booking_status","Pending");
                map.put("invoice","null");
                return map;
            }
        };
        myrequest.add(stringRequest);
    }
}