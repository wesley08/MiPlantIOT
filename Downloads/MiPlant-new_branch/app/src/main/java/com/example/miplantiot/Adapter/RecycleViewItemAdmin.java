package com.example.miplantiot.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.AdminAddingItemSHop;
import com.example.miplantiot.Model.ShopModel;
import com.example.miplantiot.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecycleViewItemAdmin extends RecyclerView.Adapter<RecycleViewItemAdmin.MyHolder>  {

    ArrayList<ShopModel> itemModels;
    private Context context;
    private FirebaseStorage mFirebaseStorage ;

    public RecycleViewItemAdmin( Context context,ArrayList<ShopModel> itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_admin,parent,false);
        return new RecycleViewItemAdmin.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final String category = itemModels.get(position).getCategory();
        final String name = itemModels.get(position).getNameItem();
        final String iditem = itemModels.get(position).getIDItem();
        final String imageurl = itemModels.get(position).getImage();
        holder.txt_shop_item_category.setText(category);
        holder.txt_shop_item_name.setText(name);
        Picasso.get().load(itemModels.get(position).getImage()).into(holder.img_image_shop_item);

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ShopItem");

        mFirebaseStorage = FirebaseStorage.getInstance();

        holder.txt_delete_item_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl(imageurl);
                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        databaseReference.child(iditem).removeValue();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
            }
        });

        holder.txt_edit_item_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, AdminAddingItemSHop.class);
                intent.putExtra("IDItem", iditem);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView txt_shop_item_category,txt_shop_item_name,txt_delete_item_admin,txt_edit_item_admin;
        ImageView img_image_shop_item;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            txt_shop_item_category = itemView.findViewById(R.id.txt_shop_item_category);
            txt_shop_item_name = itemView.findViewById(R.id.txt_shop_item_name);
            txt_delete_item_admin = itemView.findViewById(R.id.txt_delete_item_admin);
            txt_edit_item_admin = itemView.findViewById(R.id.txt_edit_item_admin);
            img_image_shop_item = itemView.findViewById(R.id.img_image_shop_item);
        }
    }
}
