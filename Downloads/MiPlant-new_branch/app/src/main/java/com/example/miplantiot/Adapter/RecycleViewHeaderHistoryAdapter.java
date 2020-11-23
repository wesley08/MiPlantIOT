package com.example.miplantiot.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Cart;
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
import java.util.HashMap;

public class RecycleViewHeaderHistoryAdapter extends RecyclerView.Adapter<RecycleViewHeaderHistoryAdapter.MyHolder> {
    Context context;
    ArrayList<HistoryHeaderDetailModel> historyHeaderDetailModels;
    RecycleViewDetailHistoryAdapter childItemAdapter;
    ArrayList<CartModel> listDetails;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    String id;


    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();


    public RecycleViewHeaderHistoryAdapter(Context context, ArrayList<HistoryHeaderDetailModel> headerViewListAdapters) {
        this.context = context;
        this.historyHeaderDetailModels = headerViewListAdapters;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_product,parent,false);
        return new RecycleViewHeaderHistoryAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();

        final String noTransaction = historyHeaderDetailModels.get(position).getNoTransaction();
        final String IDTransaction = historyHeaderDetailModels.get(position).getID();
        final String Price = historyHeaderDetailModels.get(position).getTotals();
        final String Status = historyHeaderDetailModels.get(position).getStatus();

        holder.txt_no_transaction.setText(noTransaction);
        holder.txt_totals_amount_history.setText(Price);
        holder.txt_date_transaction.setText(historyHeaderDetailModels.get(position).getDate());
        holder.txt_history_status.setText(historyHeaderDetailModels.get(position).getStatus().toUpperCase());
        if(Status.equals("Unpaid")){
            holder.txt_history_status.setBackground(res.getDrawable(R.drawable.label_unpaid));
        }else{
            holder.txt_already_pay.setVisibility(View.GONE);
        }

        final LinearLayoutManager layoutManager = new LinearLayoutManager(holder.recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("HistoryDetail").child(id).child(historyHeaderDetailModels.get(position).getID());
        reference.child("TransactionDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDetails = new ArrayList<CartModel>();
                for(DataSnapshot dataSnapshot1: snapshot.getChildren())
                {
                    CartModel p = dataSnapshot1.getValue(CartModel.class);
                    listDetails.add(p);
                }
                childItemAdapter = new RecycleViewDetailHistoryAdapter(context,listDetails);
                holder.recyclerView.setLayoutManager(layoutManager);
                holder.recyclerView.setAdapter(childItemAdapter);
                holder.recyclerView.setRecycledViewPool(viewPool);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.txt_already_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("PaymentCheck");
                final String keypush = databaseReference.push().getKey();
                HashMap<String,String> paymentcheck = new HashMap<>();
                paymentcheck.put("ID",id);
                paymentcheck.put("KeyPush",keypush);
                paymentcheck.put("Totals",Price);
                paymentcheck.put("Status",Status);
                paymentcheck.put("NoTransaction",noTransaction);
                paymentcheck.put("IDTransaction",IDTransaction);
                databaseReference.child(keypush).setValue(paymentcheck);
                holder.txt_already_pay.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public int getItemCount() {
        return historyHeaderDetailModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_no_transaction,txt_totals_amount_history,txt_date_transaction,txt_history_status,txt_already_pay;
        RecyclerView recyclerView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_already_pay =itemView.findViewById(R.id.txt_already_pay);
            txt_history_status = itemView.findViewById(R.id.txt_history_status);
            txt_no_transaction = itemView.findViewById(R.id.txt_no_transaction);
            txt_totals_amount_history = itemView.findViewById(R.id.txt_totals_amount_history);
            txt_date_transaction = itemView.findViewById(R.id.txt_date_transaction);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerview_history_details);

            auth = FirebaseAuth.getInstance();
            firebaseUser = auth.getCurrentUser();
            assert firebaseUser != null;
            id = firebaseUser.getUid();
        }
    }
}
