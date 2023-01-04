package com.example.villafilomena.Frontdesk;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

public class Request_Adapter extends RecyclerView.Adapter<Request_Adapter.ViewHolder> {
    Activity activity;
    ArrayList<Frontdesk_userDetailsModel> request_holder;
    ClickListener clickListener;

    public Request_Adapter(Activity activity, ArrayList<Frontdesk_userDetailsModel> request_holder, ClickListener clickListener) {
        this.activity = activity;
        this.request_holder = request_holder;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frontdesk_booking_requestlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Frontdesk_userDetailsModel model = request_holder.get(position);

        holder.guest_name.setText(model.getToken());
    }

    @Override
    public int getItemCount() {
        return request_holder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView guest_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guest_name = itemView.findViewById(R.id.requestList_guestName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }
    public interface ClickListener {
        void onClick(View v, int position);
    }
}
