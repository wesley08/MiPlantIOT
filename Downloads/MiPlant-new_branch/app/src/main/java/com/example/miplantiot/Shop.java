package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.miplantiot.Adapter.RecycleViewShopAdapter;
import com.example.miplantiot.Model.ShopModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Shop extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference reference;
    ArrayList<ShopModel> list;
    RecycleViewShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_shop);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference().child("ShopItem");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<ShopModel>();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    ShopModel p = dataSnapshot1.getValue(ShopModel.class);
                    list.add(p);
                }
                adapter = new RecycleViewShopAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
