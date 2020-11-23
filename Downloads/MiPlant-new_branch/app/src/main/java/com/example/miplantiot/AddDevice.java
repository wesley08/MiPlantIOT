package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miplantiot.Model.Device.Data;
import com.example.miplantiot.Model.Device.DataSoilMoisture;
import com.example.miplantiot.Model.Device.DeviceMiPlant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddDevice extends AppCompatActivity {
    EditText edt_plant_name_device,edt_plant_age_device, edt_code_device,edt_password_device;
    Button btn_add_device_user;
    DatabaseReference reference;

    private DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String id;

    DeviceMiPlant deviceMiPlant = new DeviceMiPlant();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        id = firebaseUser.getUid();

        edt_code_device = findViewById(R.id.edt_code_device);
        edt_plant_name_device = findViewById(R.id.edt_plant_name_device);
        edt_plant_age_device = findViewById(R.id.edt_plant_age_device);
        edt_password_device = findViewById(R.id.edt_password_device);
        btn_add_device_user = findViewById(R.id.btn_add_device_user);

        btn_add_device_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String codedevice = edt_code_device.getText().toString();
                final String plantname = edt_plant_name_device.getText().toString();
                final String passworddevice = edt_password_device.getText().toString();
                final String plantage = edt_plant_age_device.getText().toString();
                reference = FirebaseDatabase.getInstance().getReference().child("DeviceMiPlant").child("MP-"+codedevice);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DeviceMiPlant deviceMiPlant1 = snapshot.getValue(DeviceMiPlant.class);
                        deviceMiPlant = deviceMiPlant1;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(passworddevice.equals(deviceMiPlant.getPassword())) {
                    databaseReference = FirebaseDatabase.getInstance().getReference("DeviceMiPlant").child("MP-" + codedevice);
                    deviceMiPlant.setPlantAge(plantage);
                    deviceMiPlant.setPlantName(plantname);
                    deviceMiPlant.setIdDeviceMiPlant(id);
                    databaseReference.setValue(deviceMiPlant);
                }
            }
        });
    }
}
