package com.example.miplantiot.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miplantiot.Model.CartModel;
import com.example.miplantiot.Model.ShopModel;
import com.example.miplantiot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class RecycleViewShopAdapter extends RecyclerView.Adapter<RecycleViewShopAdapter.MyHolder>{

   ArrayList<ShopModel> shopModels;
    private Context context;
    int lastPosition = -1;
    private FirebaseUser firebaseUser;
    private FirebaseAuth auth;
    String id;
    private DatabaseReference databaseReference;

    public RecycleViewShopAdapter( Context context ,ArrayList<ShopModel> shopModels) {
        this.shopModels = shopModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop,parent,false);
        return new RecycleViewShopAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        Picasso.get().load(shopModels.get(position).getImage()).into(holder.img);

        String[] firstRow = shopModels.get(position).getFirstRow().split("\\|");
        String[] secondRow = shopModels.get(position).getSecondRow().split("\\|");
        String[] ThirdRow = shopModels.get(position).getThirdRow().split("\\|");
        String[] FourthRow = shopModels.get(position).getFourthRow().split("\\|");

        final String Category = shopModels.get(position).getCategory();
        final String NameItem = shopModels.get(position).getNameItem();
        final String UrlImage = shopModels.get(position).getImage();
        final int Price = shopModels.get(position).getPrice();
        final int Weight = shopModels.get(position).getWeight();

        holder.txt_category.setText(Category);
        holder.txt_nameItem.setText(NameItem);
        holder.txt_heightofplant.setText(shopModels.get(position).getHeightOfPlant());
        holder.txt_price_value.setText("RP "+Price);
        if(!shopModels.get(position).getFirstRow().equals("|")){
            holder.txt_row_1_column_1.setText(firstRow[0]);
            holder.txt_row_1_column_2.setText(firstRow[1]);
        }
        if(!shopModels.get(position).getSecondRow().equals("|")){
            holder.txt_row_2_column_1.setText(secondRow[0]);
            holder.txt_row_2_column_2.setText(secondRow[1]);
        }
        if(!shopModels.get(position).getThirdRow().equals("|")){
            holder.txt_row_3_column_1.setText(ThirdRow[0]);
            holder.txt_row_3_column_2.setText(ThirdRow[1]);
        }
        if(!shopModels.get(position).getFourthRow().equals("|")){
            holder.txt_row_4_column_1.setText(FourthRow[0]);
            holder.txt_row_4_column_2.setText(FourthRow[1]);
        }

        holder.txt_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReferences = FirebaseDatabase.getInstance().getReference("DetailCart").child(id).child(Category+" "+NameItem);

                ValueEventListener eventListener = new ValueEventListener(){

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            databaseReference = FirebaseDatabase.getInstance().getReference("DetailCart").child(id);
                            final String keypush = databaseReference.push().getKey();
                            CartModel CartModel = new CartModel(Category,NameItem,"Unpaid",UrlImage,keypush,Price,1,Weight);
                            databaseReference.child(Category+" "+NameItem).setValue(CartModel);
                            ToastCustom("Success Add to Cart");
                        }else{
                            CartModel p = snapshot.getValue(CartModel.class);
                            databaseReferences.child("qty").setValue(p.getQty()+1);
                            ToastCustom("Success Add to Cart");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                };

                databaseReferences.addListenerForSingleValueEvent(eventListener);
            }
        });


        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_up);
        holder.itemView.startAnimation(animation);
        lastPosition = position;

    }

    @Override
    public int getItemCount() {
        return shopModels.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_category,txt_nameItem,txt_heightofplant,txt_row_1_column_1,txt_row_1_column_2,txt_row_2_column_1,txt_row_2_column_2,
                txt_row_3_column_1,txt_row_3_column_2,txt_row_4_column_1,txt_row_4_column_2,txt_price_value,txt_add_to_cart;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                img = itemView.findViewById(R.id.img_itemshop);
                txt_category = itemView.findViewById(R.id.txt_category);
                txt_nameItem = itemView.findViewById(R.id.txt_nameItem);
                txt_heightofplant = itemView.findViewById(R.id.txt_heightofplant);
                txt_row_1_column_1 = itemView.findViewById(R.id.txt_row_1_column_1);
                txt_row_1_column_2 = itemView.findViewById(R.id.txt_row_1_column_2);
                txt_row_2_column_1 = itemView.findViewById(R.id.txt_row_2_column_1);
                txt_row_2_column_2 = itemView.findViewById(R.id.txt_row_2_column_2);
                txt_row_3_column_1 = itemView.findViewById(R.id.txt_row_3_column_1);
                txt_row_3_column_2 = itemView.findViewById(R.id.txt_row_3_column_2);
                txt_row_4_column_1 = itemView.findViewById(R.id.txt_row_4_column_1);
                txt_row_4_column_2 = itemView.findViewById(R.id.txt_row_4_column_2);
                txt_price_value = itemView.findViewById(R.id.txt_price_value);

                txt_add_to_cart = itemView.findViewById(R.id.txt_add_to_cart);

                auth = FirebaseAuth.getInstance();
                firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                id = firebaseUser.getUid();



            }
        };

    @Override
    public void onViewDetachedFromWindow(@NonNull MyHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    public void ToastCustom(String message){
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        TextView textView = (TextView) view.findViewById(R.id.txt_toast_custom);
        textView.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.TOP|Gravity.CENTER, 0, 50);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
