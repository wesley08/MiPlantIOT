package com.example.miplantiot.Adapter;

import android.content.Context;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.AddressModel;
import com.example.miplantiot.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecycleViewAddressAdapter extends RecyclerView.Adapter<RecycleViewAddressAdapter.MyHolder> {

    ArrayList<AddressModel> addressModels;
    AddressModel temp;
    int tempPosition;
    Context context;
    private int lastCheckedPosition = -1;
    private AdapterCallback mAdapterCallback;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    String id;

    public RecycleViewAddressAdapter(Context context,ArrayList<AddressModel> addressModels,AdapterCallback mAdapterCallback) {
        this.addressModels = addressModels;
        this.context = context;
        this.mAdapterCallback = mAdapterCallback;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAddressAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_address,parent,false);
        return new RecycleViewAddressAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAddressAdapter.MyHolder holder, int position) {
        holder.txt_name_receiver_view.setText(addressModels.get(position).getNameReceiver());
        holder.txt_phone_number_view.setText(addressModels.get(position).getPhoneNumber());
        holder.txt_address_view.setText(addressModels.get(position).getNameAddress()+", "+addressModels.get(position).getFullAddress()+", "+addressModels.get(position).getProvience()+", "+addressModels.get(position).getCity()+", "+addressModels.get(position).getListCity().getPostalCode());
        holder.rb_selected_address.setChecked(position == lastCheckedPosition);
    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView txt_name_receiver_view,txt_phone_number_view,txt_address_view;
        RadioButton rb_selected_address;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_receiver_view = itemView.findViewById(R.id.txt_name_receiver_view);
            txt_phone_number_view = itemView.findViewById(R.id.txt_phone_number_view);
            txt_address_view = itemView.findViewById(R.id.txt_address_view);
            rb_selected_address = itemView.findViewById(R.id.rb_selected_address);

            rb_selected_address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int copyOfLastCheckedPosition = lastCheckedPosition;
                    lastCheckedPosition = getAdapterPosition();
                    notifyItemChanged(copyOfLastCheckedPosition);
                    notifyItemChanged(lastCheckedPosition);
                    mAdapterCallback.onMethodCallback(addressModels.get(lastCheckedPosition));
                }
            });

            auth = FirebaseAuth.getInstance();
            firebaseUser = auth.getCurrentUser();
            assert firebaseUser != null;
            id = firebaseUser.getUid();

        }
    }

    public static interface AdapterCallback {
        void onMethodCallback(AddressModel addressModels);
    }
    public void deleteItem(int position) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Address");
        databaseReference.child(addressModels.get(position).getIdAddress()).removeValue();
        temp = addressModels.get(position);
        tempPosition = position;
        addressModels.remove(position);
        notifyItemRemoved(position);
    }

    private void undoDelete() {
        addressModels.add(tempPosition,
                temp);
        notifyItemInserted(tempPosition);
    }

}
