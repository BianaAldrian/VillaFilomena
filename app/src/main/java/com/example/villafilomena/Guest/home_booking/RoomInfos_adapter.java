package com.example.villafilomena.Guest.home_booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villafilomena.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RoomInfos_adapter extends RecyclerView.Adapter<RoomInfos_adapter.ViewHolder> {
    ArrayList<RoomInfos_model> roominfo_holder;

    public RoomInfos_adapter(ArrayList<RoomInfos_model> roominfo_holder) {
        this.roominfo_holder = roominfo_holder;
    }

    @NonNull
    @Override
    public RoomInfos_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_booking_roominfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomInfos_adapter.ViewHolder holder, int position) {
        final RoomInfos_model model = roominfo_holder.get(position);

        holder.RoomName.setText(model.getName());
        holder.RoomFee.setText(model.getRoom_rate());
        holder.RoomCapacity.setText(model.getRoom_capacity());
        Picasso.get().load(model.getImageUrl()).into(holder.RoomImage);
    }

    @Override
    public int getItemCount() {
        return roominfo_holder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView RoomImage;
        TextView RoomName, RoomCapacity, RoomFee;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            RoomImage = itemView.findViewById(R.id.roomInfo_RoomImage);
            RoomName = itemView.findViewById(R.id.roomInfo_RoomName);
            RoomCapacity = itemView.findViewById(R.id.roomInfo_RoomCapacity);
            RoomFee = itemView.findViewById(R.id.roomInfo_RoomFee);
        }
    }
}
