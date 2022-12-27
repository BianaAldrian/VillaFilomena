package com.example.villafilomena.Frontdesk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villafilomena.Guest.home_booking.RoomInfos_adapter;
import com.example.villafilomena.R;

import java.util.ArrayList;

public class Request_Adapter extends RecyclerView.Adapter<Request_Adapter.ViewHolder> {
    ArrayList<Book_Request_model> request_holder;

    public Request_Adapter(ArrayList<Book_Request_model> request_holder) {
        this.request_holder = request_holder;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frontdesk_booking_requestlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Request_Adapter.ViewHolder holder, int position) {
        Book_Request_model model = request_holder.get(position);
        holder.guest_name.setText(model.getUsers_email());
    }

    @Override
    public int getItemCount() {
        return request_holder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView guest_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guest_name = itemView.findViewById(R.id.requestList_guestName);
        }
    }
}
