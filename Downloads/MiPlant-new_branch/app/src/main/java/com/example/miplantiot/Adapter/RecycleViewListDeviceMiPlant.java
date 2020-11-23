package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.AddressModel;
import com.example.miplantiot.Model.Device.DeviceMiPlant;
import com.example.miplantiot.R;

import java.util.ArrayList;

public class RecycleViewListDeviceMiPlant extends RecyclerView.Adapter<RecycleViewListDeviceMiPlant.MyHolder> {
    Context context;
    ArrayList<DeviceMiPlant> deviceMiPlants;
    private AdapterCallbackList mAdapterCallback;

    public RecycleViewListDeviceMiPlant(Context context, ArrayList<DeviceMiPlant> deviceMiPlants, AdapterCallbackList mAdapterCallback) {
        this.context = context;
        this.deviceMiPlants = deviceMiPlants;
        this.mAdapterCallback = mAdapterCallback;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_device,parent,false);
        return new RecycleViewListDeviceMiPlant.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.txt_age_of_plant_list.setText(deviceMiPlants.get(position).getPlantAge());
        holder.txt_name_of_plant_list.setText(deviceMiPlants.get(position).getPlantName());
        holder.txt_id_plant_list.setText(deviceMiPlants.get(position).getId());

        holder.txt_id_plant_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapterCallback.onMethodCallback(deviceMiPlants.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return deviceMiPlants.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name_of_plant_list,txt_age_of_plant_list,txt_id_plant_list;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            txt_id_plant_list = itemView.findViewById(R.id.txt_id_plant_list);
            txt_name_of_plant_list = itemView.findViewById(R.id.txt_name_of_plant_list);
            txt_age_of_plant_list = itemView.findViewById(R.id.txt_age_of_plant_list);
        }
    }

    public static interface AdapterCallbackList {
        void onMethodCallback(String idDeviceMiPlant);
    }
}
