package com.example.villafilomena.Frontdesk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villafilomena.R;

import java.util.ArrayList;

public class Guest_arrival_departure_Adapter extends RecyclerView.Adapter<Guest_arrival_departure_Adapter.ViewHolder> {
    ArrayList<Guest_arrival_departure_model> guestHolder;

    public Guest_arrival_departure_Adapter(ArrayList<Guest_arrival_departure_model> guestHolder) {
        this.guestHolder = guestHolder;
    }

    @NonNull
    @Override
    public Guest_arrival_departure_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_arrival_departure_list, parent, false);
        return new Guest_arrival_departure_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Guest_arrival_departure_Adapter.ViewHolder holder, int position) {
        Guest_arrival_departure_model model = guestHolder.get(position);
        holder.list_no.setText(model.getList_no());
        holder.guest_name.setText(model.getGuest_name());
        holder.no_people.setText(model.getGuest_count());
        holder.price.setText(model.getPay());
        holder.balanced.setText(model.getBalance());
        holder.date.setText(model.getCheckIn_date());
        holder.time.setText(model.getCheckIn_time());
    }

    @Override
    public int getItemCount() {
        return guestHolder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView list_no, guest_name, no_people, room, price, balanced, date, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            list_no = itemView.findViewById(R.id.guestList_No);
            guest_name = itemView.findViewById(R.id.guestList_guestname);
            no_people = itemView.findViewById(R.id.guestList_noPeople);
            room = itemView.findViewById(R.id.guestList_room);
            price = itemView.findViewById(R.id.guestList_price);
            balanced = itemView.findViewById(R.id.guestList_balanced);
            date = itemView.findViewById(R.id.guestList_date);
            time = itemView.findViewById(R.id.guestList_time);
        }
    }
}
