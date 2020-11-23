package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.CartModel;
import com.example.miplantiot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewCartAdapter extends RecyclerView.Adapter<RecycleViewCartAdapter.MyHolder> {

    Context context;
    ArrayList<CartModel> cartModels;
    int lastPosition = -1;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    String id;

    public RecycleViewCartAdapter(Context context, ArrayList<CartModel> cartModels) {
        this.context = context;
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public RecycleViewCartAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return new RecycleViewCartAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecycleViewCartAdapter.MyHolder holder, final int position) {
        Picasso.get().load(cartModels.get(position).getUrlImage()).into(holder.img);

        final int Qty = cartModels.get(position).getQty();

        final String ProductID = cartModels.get(position).getCategory()+" "+cartModels.get(position).getNameProduct();

        holder.txt_cart_cateogry_item.setText(cartModels.get(position).getCategory());
        holder.txt_cart_name_of_item.setText(cartModels.get(position).getNameProduct());
        holder.txt_cart_price.setText("Rp "+cartModels.get(position).getPrice());
        holder.edt_cart_number_of_item.setText(cartModels.get(position).getQty()+"");


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DetailCart").child(id);

        holder.txt_cart_plus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(ProductID).child("qty").setValue(Qty+1);
            }
        });

        holder.txt_cart_minus_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Qty-1==0){
                    databaseReference.child(ProductID).removeValue();
                }else {
                    databaseReference.child(ProductID).child("qty").setValue(Qty - 1);
                }
            }
        });


        holder.txt_remove_item_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(ProductID).removeValue();
            }
        });

        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_cart_cateogry_item,txt_cart_minus_item,txt_cart_plus_item,txt_cart_name_of_item,txt_cart_price,txt_remove_item_cart,edt_cart_number_of_item;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_image_cart);

            txt_cart_cateogry_item = itemView.findViewById(R.id.txt_cart_cateogry_item);
            txt_cart_minus_item = itemView.findViewById(R.id.txt_cart_minus_item);
            txt_cart_plus_item = itemView.findViewById(R.id.txt_cart_plus_item);
            txt_cart_name_of_item = itemView.findViewById(R.id.txt_cart_name_of_item);
            txt_cart_price = itemView.findViewById(R.id.txt_cart_price);
            txt_remove_item_cart = itemView.findViewById(R.id.txt_remove_item_cart);
            edt_cart_number_of_item = itemView.findViewById(R.id.edt_cart_number_of_item);

            auth = FirebaseAuth.getInstance();
            firebaseUser = auth.getCurrentUser();
            assert firebaseUser != null;
            id = firebaseUser.getUid();

        }
    }


}
