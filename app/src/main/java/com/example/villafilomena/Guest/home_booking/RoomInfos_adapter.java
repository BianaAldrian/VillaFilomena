package com.example.villafilomena.Guest.home_booking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_booking_roominfo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomInfos_adapter.ViewHolder holder, int position) {
        final RoomInfos_model model = roominfo_holder.get(position);

        if(holder.RoomImage.getDrawable() == null){
            holder.room_Progress.setVisibility(View.VISIBLE);
        }else{
            holder.room_Progress.setVisibility(View.GONE);
        }
        holder.RoomName.setText(model.getName());
        holder.RoomFee.setText(model.getRoom_rate());
        holder.RoomCapacity.setText(model.getRoom_capacity());
        Picasso.get().load(model.getImageUrl()).into(holder.RoomImage);

        holder.RoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.room_Check.getVisibility() == View.GONE){
                    holder.room_Check.setVisibility(View.VISIBLE);
                }else {
                    holder.room_Check.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return roominfo_holder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView room_Check;
        ProgressBar room_Progress;
        ImageView RoomImage;
        TextView RoomName, RoomCapacity, RoomFee;

        public ViewHolder(View view) {
            super(view);
            room_Check = view.findViewById(R.id.roomInfo_Check);
            room_Progress = view.findViewById(R.id.roomInfo_Progess);
            RoomImage = view.findViewById(R.id.roomInfo_RoomImage);
            RoomName = view.findViewById(R.id.roomInfo_RoomName);
            RoomCapacity = view.findViewById(R.id.roomInfo_RoomCapacity);
            RoomFee = view.findViewById(R.id.roomInfo_RoomFee);
        }

    }
}
