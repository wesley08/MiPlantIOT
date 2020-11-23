package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.miplantiot.Adapter.RecycleViewAdminApproval;
import com.example.miplantiot.Adapter.RecycleViewHeaderHistoryAdapter;
import com.example.miplantiot.Model.ApprovalModel;
import com.example.miplantiot.Model.HistoryHeaderDetailModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApprovelPaymentCustomer extends AppCompatActivity {
    RecyclerView recyclerView;
    RecycleViewAdminApproval adapter;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String id;
    ArrayList<ApprovalModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvel_payment_customer);


        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        id = firebaseUser.getUid();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_approval_payment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final Query query = FirebaseDatabase.getInstance().getReference("PaymentCheck");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<ApprovalModel>();
                String keyTempo="";
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    ApprovalModel p = dataSnapshot1.getValue(ApprovalModel.class);
                    keyTempo = dataSnapshot1.getRef().toString();
                    list.add(p);
                }

                adapter = new RecycleViewAdminApproval(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
