package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.Device.Data;
import com.example.miplantiot.Model.Device.DeviceMiPlant;
import com.example.miplantiot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleViewDeviceMiplant extends RecyclerView.Adapter<RecycleViewDeviceMiplant.MyHolder> {
    Context context;
    ArrayList<DeviceMiPlant> deviceMiPlants;
    ArrayList<Data> data;

    public RecycleViewDeviceMiplant(Context context, ArrayList<DeviceMiPlant> deviceMiPlants) {
        this.context = context;
        this.deviceMiPlants = deviceMiPlants;
    }

    @NonNull
    @Override
    public RecycleViewDeviceMiplant.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_device_miplant,parent,false);
        return new RecycleViewDeviceMiplant.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewDeviceMiplant.MyHolder holder, int position) {
        holder.txt_age_of_plant.setText(deviceMiPlants.get(position).getPlantAge());
        holder.txt_name_of_plant.setText(deviceMiPlants.get(position).getPlantName());

        final Query query = FirebaseDatabase.getInstance().getReference("DeviceMiPlant").child(deviceMiPlants.get(position).getId()).child("data");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.txt_sensor_soil_content.setText(snapshot.child("SensorSoilContent").getValue(Integer.class)+"");
                holder.txt_sensor_soil_moisture.setText(snapshot.child("SensorSoilMoisture").getValue(Integer.class)+"");
                holder.txt_sensor_water_level.setText(snapshot.child("SensorWaterLevel").getValue(Integer.class)+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return deviceMiPlants.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name_of_plant,txt_age_of_plant,txt_sensor_soil_moisture,txt_sensor_soil_content,txt_sensor_water_level;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_of_plant = itemView.findViewById(R.id.txt_name_of_plant);
            txt_age_of_plant = itemView.findViewById(R.id.txt_age_of_plant);
            txt_sensor_soil_moisture = itemView.findViewById(R.id.txt_sensor_soil_moisture);
            txt_sensor_soil_content = itemView.findViewById(R.id.txt_sensor_soil_content);
            txt_sensor_water_level = itemView.findViewById(R.id.txt_sensor_water_level);
        }
    }
}
