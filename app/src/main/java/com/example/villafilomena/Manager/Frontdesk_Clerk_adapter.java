package com.example.villafilomena.Manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villafilomena.Frontdesk.Request_Adapter;
import com.example.villafilomena.R;

import java.util.ArrayList;

public class Frontdesk_Clerk_adapter extends RecyclerView.Adapter<Frontdesk_Clerk_adapter.ViewHolder>{
    ArrayList<Frontdesk_Clerk_model> clerk_holder;

    public Frontdesk_Clerk_adapter(ArrayList<Frontdesk_Clerk_model> clerk_holder) {
        this.clerk_holder = clerk_holder;
    }

    @NonNull
    @Override
    public Frontdesk_Clerk_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frontdesk_clerk_list, parent, false);
        return new Frontdesk_Clerk_adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Frontdesk_Clerk_adapter.ViewHolder holder, int position) {
        Frontdesk_Clerk_model model = clerk_holder.get(position);
        holder.clerkname.setText(model.getClerk_name());
        holder.clerkusername.setText(model.getClerk_username());
        holder.clerkpassword.setText(model.getClerk_password());
    }

    @Override
    public int getItemCount() {
        return clerk_holder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView clerkname, clerkusername, clerkpassword;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clerkname = itemView.findViewById(R.id.list_cleark_name);
            clerkusername = itemView.findViewById(R.id.list_clerk_username);
            clerkpassword = itemView.findViewById(R.id.list_clerk_password);
        }
    }
}
