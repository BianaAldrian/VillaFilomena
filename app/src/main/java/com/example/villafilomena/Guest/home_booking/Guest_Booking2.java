package com.example.villafilomena.Guest.home_booking;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.villafilomena.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Guest_Booking2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Guest_Booking2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Guest_Booking2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Guest_Booking2.
     */
    // TODO: Rename and change types and number of parameters
    public static Guest_Booking2 newInstance(String param1, String param2) {
        Guest_Booking2 fragment = new Guest_Booking2();
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

    public static TextView txtCheckInOut,txtKidAdult;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.guest_booking2, container, false);

        txtCheckInOut = view.findViewById(R.id.guestBooking2_txtchekcIn_Out);
        txtKidAdult = view.findViewById(R.id.guestBooking2_txtKid_Adult);

        txtCheckInOut.setText(Guest_Booking.txtCheckInOut.getText().toString());
        txtKidAdult.setText(Guest_Booking.txtKidAdult.getText().toString());

        return view;
    }
}