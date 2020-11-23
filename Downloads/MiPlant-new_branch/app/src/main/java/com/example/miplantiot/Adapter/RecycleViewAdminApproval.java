package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.ApprovalModel;
import com.example.miplantiot.Model.CartModel;
import com.example.miplantiot.Model.HistoryHeaderDetailModel;
import com.example.miplantiot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleViewAdminApproval extends RecyclerView.Adapter<RecycleViewAdminApproval.MyHolder> {

    Context context;
    ArrayList<ApprovalModel> historyHeaderDetailModels;
    RecycleViewDetailHistoryAdapter childItemAdapter;
    ArrayList<CartModel> listDetails;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    String id;

    public RecycleViewAdminApproval(Context context, ArrayList<ApprovalModel> historyHeaderDetailModels) {
        this.context = context;
        this.historyHeaderDetailModels = historyHeaderDetailModels;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_approvel_payment,parent,false);
        return new RecycleViewAdminApproval.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.txt_no_transaction_admin.setText(historyHeaderDetailModels.get(position).getNoTransaction());
        holder.txt_totals_amount_history_admin.setText(historyHeaderDetailModels.get(position).getTotals());
        holder.txt_history_status_admin.setText(historyHeaderDetailModels.get(position).getStatus());

        final String id = historyHeaderDetailModels.get(position).getID();
        final String idTransaction = historyHeaderDetailModels.get(position).getIDTransaction();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HistoryDetail").child(id).child(idTransaction);

        holder.txt_paid_item_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Status").setValue("Paid");

            }
        });

        holder.txt_unpaid_item_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Status").setValue("Unpaid");
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyHeaderDetailModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_no_transaction_admin,txt_totals_amount_history_admin,txt_history_status_admin,txt_paid_item_admin,txt_unpaid_item_admin;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_history_status_admin = itemView.findViewById(R.id.txt_history_status_admin);
            txt_no_transaction_admin = itemView.findViewById(R.id.txt_no_transaction_admin);
            txt_paid_item_admin = itemView.findViewById(R.id.txt_paid_item_admin);
            txt_unpaid_item_admin = itemView.findViewById(R.id.txt_unpaid_item_admin);
            txt_totals_amount_history_admin = itemView.findViewById(R.id.txt_totals_amount_history_admin);


        }
    }
}
