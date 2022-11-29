package com.example.villafilomena.Guest.home_reservation;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.villafilomena.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Reservation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Reservation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Reservation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Reservation.
     */
    // TODO: Rename and change types and number of parameters
    public static Reservation newInstance(String param1, String param2) {
        Reservation fragment = new Reservation();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reservation, container, false);

        checkIn_checkOut = view.findViewById(R.id.checkIn_checkOut);
        reserve_continue = view.findViewById(R.id.reserve_continue);
        adult_child = view.findViewById(R.id.adult_child);

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

        
        return view;
    }
}