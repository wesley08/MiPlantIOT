package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miplantiot.Adapter.CityAdapter;
import com.example.miplantiot.Adapter.ProvinceAdapter;
import com.example.miplantiot.Adapter.RecycleViewAddressAdapter;
import com.example.miplantiot.Adapter.RecycleViewCartAdapter;
import com.example.miplantiot.Adapter.RecyclerViewOptionCourier;
import com.example.miplantiot.Model.AddressModel;
import com.example.miplantiot.Model.CartModel;
import com.example.miplantiot.Model.city.ItemCity;
import com.example.miplantiot.Model.cost.ItemCost;
import com.example.miplantiot.Model.CourierCostModel;
import com.example.miplantiot.Model.province.ItemProvince;
import com.example.miplantiot.Model.province.Result;
import com.example.miplantiot.api.ApiService;
import com.example.miplantiot.api.ApiUrl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Cart extends AppCompatActivity implements RecycleViewAddressAdapter.AdapterCallback {

    private DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    String id;
    int total_amount_global = 0;
    int total_weight_global = 0;

    private AlertDialog.Builder alert;
    private AlertDialog ad;
    private EditText searchList;
    private ListView mListView;
    private ProgressDialog progressDialog;

    ArrayList<CourierCostModel> courierModel;
    ArrayList<CartModel> list;
    ArrayList<AddressModel> addressModels;
    private List<Result> ListProvince = new ArrayList<>();
    private Result ListProvinceTemp = new Result();
    private CityAdapter adapter_city;
    private List<com.example.miplantiot.Model.city.Result> ListCity = new ArrayList<>();
    private com.example.miplantiot.Model.city.Result ListCityTemp = new com.example.miplantiot.Model.city.Result();

    AddressModel myObject;

    RecycleViewCartAdapter adapter;
    RecycleViewAddressAdapter addressAdapter;
    RecyclerViewOptionCourier courierAdapter;
    RecyclerView recyclerView, recyclerViewaddress,recyclerview_select_courier;
    private ProvinceAdapter adapter_province;

    TextView txt_amount_of_items, txt_amount_of_totals, txt_add_cancel_address, btn_confirm_checkout,btn_confirm_address;
    Button btn_check_out, btn_add_address;
    LinearLayout ll_add_address;
    ScrollView sv_add_address;
    EditText edt_name_address_add, edt_name_receiver_add, edt_phone_number_add, edt_city_add, edt_full_address_add, edt_province_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        assert firebaseUser != null;
        id = firebaseUser.getUid();

        txt_amount_of_totals = findViewById(R.id.txt_amount_of_totals);
        btn_check_out = findViewById(R.id.btn_check_out);
        txt_amount_of_items = findViewById(R.id.txt_amount_of_items);

        recyclerView = findViewById(R.id.recyclerview_cart);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Cart.this);
        final View mView = getLayoutInflater().inflate(R.layout.modal_select_address, null);

        ll_add_address = mView.findViewById(R.id.ll_add_address);
        sv_add_address = mView.findViewById(R.id.sv_add_address);
        txt_add_cancel_address = mView.findViewById(R.id.txt_add_cancel_address);

        btn_add_address = mView.findViewById(R.id.btn_add_address);
        btn_confirm_address = mView.findViewById(R.id.btn_confirm_address);

        edt_name_address_add = mView.findViewById(R.id.edt_name_address_add);
        edt_name_receiver_add = mView.findViewById(R.id.edt_name_receiver_add);
        edt_phone_number_add = mView.findViewById(R.id.edt_phone_number_add);
        edt_city_add = mView.findViewById(R.id.edt_city_add);
        edt_full_address_add = mView.findViewById(R.id.edt_full_address_add);
        edt_province_add = mView.findViewById(R.id.edt_provience_add);

        recyclerViewaddress =  mView.findViewById(R.id.recyclerview_address);
        recyclerViewaddress.setHasFixedSize(true);
        recyclerViewaddress.setLayoutManager(new LinearLayoutManager(this));

        final AlertDialog.Builder mBuilderCourier = new AlertDialog.Builder(Cart.this);
        final View mViewCourier = getLayoutInflater().inflate(R.layout.modal_select_courier, null);

        btn_confirm_checkout = mViewCourier.findViewById(R.id.btn_confirm_checkout);

        recyclerview_select_courier =  mViewCourier.findViewById(R.id.recyclerview_select_courier);
        recyclerview_select_courier.setHasFixedSize(true);
        recyclerview_select_courier.setLayoutManager(new LinearLayoutManager(this));


        final Query query = FirebaseDatabase.getInstance().getReference("DetailCart").child(id).orderByChild("status").equalTo("Unpaid");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                int total_amount = 0;
                int total_weight=0;

                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                    CartModel p = dataSnapshot1.getValue(CartModel.class);
                    total_amount += p.getPrice() * p.getQty();
                    total_weight+= p.getWeight()*p.getQty();
                    list.add(p);
                }
                adapter = new RecycleViewCartAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
                txt_amount_of_items.setText(list.size() + " Items");
                txt_amount_of_totals.setText(total_amount + "");
                total_amount_global = total_amount;
                total_weight_global = total_weight;
                Toast.makeText(Cart.this, total_weight_global+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edt_province_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpProvince(edt_province_add, edt_city_add);
            }
        });

        edt_city_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (edt_province_add.getTag().equals("")) {
                        edt_province_add.setError("Please chooise your form province");
                    } else {
                        popUpCity(edt_city_add, edt_province_add);
                    }

                } catch (NullPointerException e) {
                    edt_city_add.setError("Please chooise your form province");
                }
            }
        });


        btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_add_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (txt_add_cancel_address.getText().equals("add address")) {
                            sv_add_address.setVisibility(View.VISIBLE);
                            txt_add_cancel_address.setText("cancel add address");
                            txt_add_cancel_address.setBackgroundColor(getResources().getColor(R.color.colorWarning));
                        } else {
                            sv_add_address.setVisibility(View.GONE);
                            txt_add_cancel_address.setText("add address");
                            txt_add_cancel_address.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                        }
                    }
                });

                btn_add_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String nameAddress = edt_name_address_add.getText().toString();
                        String nameReceiver = edt_name_receiver_add.getText().toString();
                        String phoneNumber = edt_phone_number_add.getText().toString();
                        String city = edt_city_add.getText().toString();
                        String fullAddress = edt_full_address_add.getText().toString();
                        String province = edt_province_add.getText().toString();


                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Address");
                        final String keypush = databaseReference.push().getKey();

                        AddressModel addressModel = new AddressModel();
                        addressModel.setProvience(province);
                        addressModel.setCity(city);
                        addressModel.setListCity(ListCityTemp);
                        addressModel.setListProvince(ListProvinceTemp);
                        addressModel.setIdAddress(keypush);
                        addressModel.setNameAddress(nameAddress);
                        addressModel.setNameReceiver(nameReceiver);
                        addressModel.setPhoneNumber(phoneNumber);
                        addressModel.setFullAddress(fullAddress);

                        databaseReference.child(keypush).setValue(addressModel);

                    }
                });

                final Query query = FirebaseDatabase.getInstance().getReference("Users").child(id).child("Address");

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        addressModels = new ArrayList<>();

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            AddressModel p = dataSnapshot1.getValue(AddressModel.class);
                            addressModels.add(p);
                        }
                        addressAdapter = new RecycleViewAddressAdapter(getApplicationContext(), addressModels, Cart.this);
                        ItemTouchHelper itemTouchHelper = new
                                ItemTouchHelper(new SwipeToDeleteCallBack(addressAdapter));
                        itemTouchHelper.attachToRecyclerView(recyclerViewaddress);
                        recyclerViewaddress.setAdapter(addressAdapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                if(mView.getParent() != null) {
                    ((ViewGroup)mView.getParent()).removeView(mView); // <- fix
                }


                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                btn_confirm_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(mViewCourier.getParent() != null) {
                            ((ViewGroup)mViewCourier.getParent()).removeView(mViewCourier); // <- fix
                        }

                        mBuilderCourier.setView(mViewCourier);
                        AlertDialog dialogCourier = mBuilderCourier.create();
                        dialogCourier.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialogCourier.show();

                        btn_confirm_checkout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                databaseReference = FirebaseDatabase.getInstance().getReference("HistoryDetail").child(id);
                                int ongkir = Integer.parseInt(courierAdapter.getCostCourier());
                                final String dateTransaction = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                                final String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put("Date", dateTransaction);
                                hashMap.put("NoTransaction", "MI" + timeStamp);
                                hashMap.put("Status", "Unpaid");
                                hashMap.put("Ongkir", ongkir+"");
                                hashMap.put("TotalsBelanja", total_amount_global+"");
                                hashMap.put("Totals", (total_amount_global+ongkir)+"");
                                hashMap.put("ID", id + timeStamp);
                                hashMap.put("Weight", total_weight_global+"");

                                databaseReference.child(id + timeStamp).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        databaseReference.child(id + timeStamp).child("Address").setValue(myObject);
                                        databaseReference.child(id + timeStamp).child("TransactionDetail").setValue(list);
                                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DetailCart").child(id);
                                        databaseReference.removeValue();
                                    }
                                });

                                Intent intent = new Intent(Cart.this, HistoryCheckOut.class);
                                startActivity(intent);
                            }
                        });
                    }
                });

            }
        });


    }

    public void popUpProvince(final EditText etProvince, final EditText etCity) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(Cart.this);
        alert.setTitle("List Province");
        alert.setMessage("select your province");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList =  alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherProvince(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView = alertLayout.findViewById(R.id.listItem);

        ListProvince.clear();
        adapter_province = new ProvinceAdapter(Cart.this, ListProvince);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                Result cn = (Result) o;
                ListProvinceTemp = cn;
                etProvince.setError(null);
                etProvince.setText(cn.getProvince());
                etProvince.setTag(cn.getProvinceId());

                etCity.setText("");
                etCity.setTag("");

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(Cart.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getProvince();

    }

    public void popUpCity(final EditText etCity, final EditText etProvince) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View alertLayout = inflater.inflate(R.layout.custom_dialog_search, null);

        alert = new AlertDialog.Builder(Cart.this);
        alert.setTitle("List City");
        alert.setMessage("select your city");
        alert.setView(alertLayout);
        alert.setCancelable(true);

        ad = alert.show();

        searchList = alertLayout.findViewById(R.id.searchItem);
        searchList.addTextChangedListener(new MyTextWatcherCity(searchList));
        searchList.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        mListView =  alertLayout.findViewById(R.id.listItem);

        ListCity.clear();
        adapter_city = new CityAdapter(Cart.this, ListCity);
        mListView.setClickable(true);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object o = mListView.getItemAtPosition(i);
                com.example.miplantiot.Model.city.Result cn = (com.example.miplantiot.Model.city.Result) o;
                ListCityTemp = cn;
                etCity.setError(null);
                etCity.setText(cn.getCityName());
                etCity.setTag(cn.getCityId());

                ad.dismiss();
            }
        });

        progressDialog = new ProgressDialog(Cart.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        getCity(etProvince.getTag().toString());

    }

    private class MyTextWatcherCity implements TextWatcher {

        private View view;

        private MyTextWatcherCity(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            if (view.getId() == R.id.searchItem) {
                adapter_city.filter(editable.toString());
            }
        }
    }

    private class MyTextWatcherProvince implements TextWatcher {

        private View view;

        private MyTextWatcherProvince(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence s, int i, int before, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            if (view.getId() == R.id.searchItem) {
                adapter_province.filter(editable.toString());
            }
        }
    }

    public void getProvince() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemProvince> call = service.getProvince();

        call.enqueue(new Callback<ItemProvince>() {
            @Override
            public void onResponse(Call<ItemProvince> call, Response<ItemProvince> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        Result itemProvince = new Result(
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince()
                        );

                        ListProvince.add(itemProvince);
                        mListView.setAdapter(adapter_province);
                    }

                    adapter_province.setList(ListProvince);
                    adapter_province.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(Cart.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemProvince> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Cart.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void onMethodCallback(AddressModel addressModels) {

        myObject = addressModels;

        String[] str = {"tiki","jne","pos"};
        courierModel = new ArrayList<>();

        for (String s : str) {
            getCoast(
                    "151",
                    myObject.getListCity().getCityId(),
                    total_weight_global + "",
                    s
            );
        }

    }

    public void getCity(String id_province) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCity> call = service.getCity(id_province);

        call.enqueue(new Callback<ItemCity>() {
            @Override
            public void onResponse(Call<ItemCity> call, Response<ItemCity> response) {

                progressDialog.dismiss();
                Log.v("wow", "json : " + new Gson().toJson(response));

                if (response.isSuccessful()) {

                    int count_data = response.body().getRajaongkir().getResults().size();
                    for (int a = 0; a <= count_data - 1; a++) {
                        com.example.miplantiot.Model.city.Result itemProvince = new com.example.miplantiot.Model.city.Result(
                                response.body().getRajaongkir().getResults().get(a).getCityId(),
                                response.body().getRajaongkir().getResults().get(a).getProvinceId(),
                                response.body().getRajaongkir().getResults().get(a).getProvince(),
                                response.body().getRajaongkir().getResults().get(a).getType(),
                                response.body().getRajaongkir().getResults().get(a).getCityName(),
                                response.body().getRajaongkir().getResults().get(a).getPostalCode()
                        );

                        ListCity.add(itemProvince);
                        mListView.setAdapter(adapter_city);
                    }

                    adapter_city.setList(ListCity);
                    adapter_city.filter("");

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(Cart.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemCity> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Cart.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCoast(String origin,
                         String destination,
                         String weight,
                         final String courier) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT_HTTPS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);
        Call<ItemCost> call = service.getCost(
                "912cc0ec0d69b9b24d1b47173598e16c",
                origin,
                destination,
                weight,
                courier
        );

        call.enqueue(new Callback<ItemCost>() {
            @Override
            public void onResponse(Call<ItemCost> call, Response<ItemCost> response) {
                Log.v("wow", "json : " + new Gson().toJson(response));
                if (response.isSuccessful()) {

                    int statusCode = response.body().getRajaongkir().getStatus().getCode();

                    if (statusCode == 200){
                        try {
                            CourierCostModel courierCostModelTemp = new CourierCostModel();
                            courierCostModelTemp.setDestination(response.body().getRajaongkir().getDestinationDetails().getCityName());
                            courierCostModelTemp.setCost(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getValue().toString());
                            courierCostModelTemp.setTime(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getCost().get(0).getEtd()+" (Days)");
                            courierCostModelTemp.setCouriername(response.body().getRajaongkir().getResults().get(0).getCosts().get(0).getDescription()+" ("+ response.body().getRajaongkir().getResults().get(0).getName()+"))");

                            courierModel.add(courierCostModelTemp);
                        }catch (IndexOutOfBoundsException e){
                            Toast.makeText(Cart.this, courier+" doesn't have it now", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        String message = response.body().getRajaongkir().getStatus().getDescription();
                        Toast.makeText(Cart.this, message, Toast.LENGTH_SHORT).show();
                    }
                    courierAdapter = new RecyclerViewOptionCourier(getApplicationContext(), courierModel);
                    recyclerview_select_courier.setAdapter(courierAdapter);

                } else {
                    String error = "Error Retrive Data from Server !!!";
                    Toast.makeText(Cart.this, error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ItemCost> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Cart.this, "Message : Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
