package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.CourierCostModel;
import com.example.miplantiot.R;

import java.util.ArrayList;

public class RecyclerViewOptionCourier extends RecyclerView.Adapter<RecyclerViewOptionCourier.MyHolder> {
    Context context;
    ArrayList<CourierCostModel> courierCostModels;
    private int lastCheckedPosition = -1;
    String costCourier ="";


    public RecyclerViewOptionCourier(Context context, ArrayList<CourierCostModel> courierCostModels) {
        this.context = context;
        this.courierCostModels = courierCostModels;
    }

    @NonNull
    @Override
    public RecyclerViewOptionCourier.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_courier,parent,false);
        return new RecyclerViewOptionCourier.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewOptionCourier.MyHolder holder, int position) {

        holder.txt_cost_view.setText("Rp. "+courierCostModels.get(position).getCost());
        holder.txt_destination_time_view.setText(courierCostModels.get(position).getDestination()+" - "+courierCostModels.get(position).getTime());
        holder.txt_name_expedisi_view.setText(courierCostModels.get(position).getCouriername());
        holder.rb_selected_courier.setChecked(position == lastCheckedPosition);

    }

    @Override
    public int getItemCount() {
        return courierCostModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name_expedisi_view,txt_destination_time_view,txt_cost_view;
        RadioButton rb_selected_courier;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_expedisi_view = itemView.findViewById(R.id.txt_name_expedisi_view);
            txt_destination_time_view = itemView.findViewById(R.id.txt_destination_time_view);
            txt_cost_view = itemView.findViewById(R.id.txt_cost_view);
            rb_selected_courier = itemView.findViewById(R.id.rb_selected_courier);
            rb_selected_courier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                    costCourier = courierCostModels.get(lastCheckedPosition).getCost();
                }
            });
        }
    }

    public String getCostCourier() {
        return costCourier;
    }
}
