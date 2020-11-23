package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.CartModel;
import com.example.miplantiot.R;

import java.util.ArrayList;

public class RecycleViewDetailHistoryAdapter extends RecyclerView.Adapter<RecycleViewDetailHistoryAdapter.MyHolder> {

    Context context;
    ArrayList<CartModel> cartModels;

    public RecycleViewDetailHistoryAdapter(Context context, ArrayList<CartModel> cartModels) {
        this.context = context;
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public RecycleViewDetailHistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_history,parent,false);
        return new RecycleViewDetailHistoryAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewDetailHistoryAdapter.MyHolder holder, int position) {
        holder.txt_name_of_prodcut_detail.setText(String.valueOf(cartModels.get(position).getNameProduct()));
        holder.txt_qty_of_prodcut_detail.setText(String.valueOf(cartModels.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name_of_prodcut_detail,txt_qty_of_prodcut_detail;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_of_prodcut_detail = itemView.findViewById(R.id.txt_name_of_prodcut_detail);
            txt_qty_of_prodcut_detail = itemView.findViewById(R.id.txt_qty_of_product_detail);
        }
    }
}
