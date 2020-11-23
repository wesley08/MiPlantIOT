package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.miplantiot.Adapter.RecycleViewCartAdapter;
import com.example.miplantiot.Adapter.RecycleViewHeaderHistoryAdapter;
import com.example.miplantiot.Model.CartModel;
import com.example.miplantiot.Model.HistoryHeaderDetailModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryCheckOut extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleViewHeaderHistoryAdapter adapter;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String id;
    ArrayList<HistoryHeaderDetailModel> list;
    ArrayList<CartModel> listDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_check_out);
        
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        id = firebaseUser.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_history_product);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Query query = FirebaseDatabase.getInstance().getReference("HistoryDetail").child(id);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<HistoryHeaderDetailModel>();
                String keyTempo="";
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    HistoryHeaderDetailModel p = dataSnapshot1.getValue(HistoryHeaderDetailModel.class);
                    keyTempo = dataSnapshot1.getRef().toString();
                    list.add(p);
                }

                adapter = new RecycleViewHeaderHistoryAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
