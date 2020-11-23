package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.miplantiot.Adapter.RecycleViewDeviceMiplant;
import com.example.miplantiot.Adapter.RecycleViewListDeviceMiPlant;
import com.example.miplantiot.Model.Device.DeviceMiPlant;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity implements RecycleViewListDeviceMiPlant.AdapterCallbackList, NavigationView.OnNavigationItemSelectedListener{
    Button btn_logout,btn_shop,btn_add_item_shop,btn_cart,btn_history_details,btn_list_item_shop,btn_approval_payment_customer,btn_tester,btn_add_device_user;
    private  FirebaseAuth auth;
    LinearLayout ll_menu;
    RecyclerView recyclerView, recyclerViewListDevice;
    RecycleViewListDeviceMiPlant recycleViewListDeviceMiPlant;
    RecycleViewDeviceMiplant recycleViewDeviceMiplant;
    FirebaseUser firebaseUser;
    String id;
    ArrayList<DeviceMiPlant> deviceMiPlants;
    ArrayList<DeviceMiPlant> listdeviceMiPlants;
    String IdDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        id = firebaseUser.getUid();

        ll_menu = findViewById(R.id.ll_menu);
        btn_cart =findViewById(R.id.btn_cart);
        btn_tester =findViewById(R.id.btn_tester);
        btn_logout = findViewById(R.id.btn_logout);
        btn_add_item_shop = findViewById(R.id.btn_add_item_shop);
        btn_shop = findViewById(R.id.btn_shop);
        btn_history_details = findViewById(R.id.btn_history_details);
        btn_list_item_shop = findViewById(R.id.btn_list_item_shop);
        btn_approval_payment_customer = findViewById(R.id.btn_approval_payment_customer);
        btn_add_device_user = findViewById(R.id.btn_add_device_user);

        recyclerView = findViewById(R.id.recyclerview_devicemiplant);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewListDevice = findViewById(R.id.recyclerview_listdevicemiplant);
        recyclerViewListDevice.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewListDevice.setLayoutManager(layoutManager);

        final Query query1 = FirebaseDatabase.getInstance().getReference("Users").child(id).child("DeviceMiPlant");

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listdeviceMiPlants = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    DeviceMiPlant p = dataSnapshot1.getValue(DeviceMiPlant.class);
                    listdeviceMiPlants.add(p);
                }
                recycleViewListDeviceMiPlant = new RecycleViewListDeviceMiPlant(getApplicationContext(), listdeviceMiPlants, HomePage.this);
                recyclerViewListDevice.setAdapter(recycleViewListDeviceMiPlant);

                final Query query = FirebaseDatabase.getInstance().getReference("DeviceMiPlant").orderByChild("id").equalTo(listdeviceMiPlants.get(0).getId());

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        deviceMiPlants = new ArrayList<>();
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            DeviceMiPlant p = dataSnapshot1.getValue(DeviceMiPlant.class);
                            deviceMiPlants.add(p);
                        }
                        recycleViewDeviceMiplant = new RecycleViewDeviceMiplant(getApplicationContext(), deviceMiPlants);
                        recyclerView.setAdapter(recycleViewDeviceMiplant);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        ll_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        changeActivityButton(btn_approval_payment_customer,HomePage.this,ApprovelPaymentCustomer.class);
        changeActivityButton(btn_list_item_shop,HomePage.this,ListItemAdmin.class);
        changeActivityButton(btn_shop,HomePage.this,Shop.class);
        changeActivityButton(btn_add_item_shop,HomePage.this,AdminAddingItemSHop.class);
        changeActivityButton(btn_cart,HomePage.this,Cart.class);
        changeActivityButton(btn_history_details,HomePage.this,HistoryCheckOut.class);
        changeActivityButton(btn_tester,HomePage.this,Tester.class);
        changeActivityButton(btn_add_device_user,HomePage.this,AddDevice.class);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.side_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onMethodCallback(String idDeviceMiPlant) {

        final Query query = FirebaseDatabase.getInstance().getReference("DeviceMiPlant").orderByChild("id").equalTo(idDeviceMiPlant == null?IdDeviceList: idDeviceMiPlant);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                deviceMiPlants = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    DeviceMiPlant p = dataSnapshot1.getValue(DeviceMiPlant.class);
                    deviceMiPlants.add(p);
                }
                recycleViewDeviceMiplant = new RecycleViewDeviceMiplant(getApplicationContext(), deviceMiPlants);
                recyclerView.setAdapter(recycleViewDeviceMiplant);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void changeActivityButton(Button button, final Context currentClass, final Class destinationClass ){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentClass, destinationClass);
                startActivity(intent);
            }
        });
    }
}
