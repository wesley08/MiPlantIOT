package com.example.miplantiot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miplantiot.Adapter.RecycleViewItemAdmin;
import com.example.miplantiot.Model.ShopModel;
import com.example.miplantiot.fragment.FragmentSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class AdminAddingItemSHop extends AppCompatActivity {

    EditText edt_weight,edt_name_item,edt_price,edt_height_of_plant,edt_table_row_1_column_1,edt_table_row_1_column_2,edt_table_row_2_column_1,edt_table_row_2_column_2, edt_table_row_3_column_1,edt_table_row_3_column_2,edt_table_row_4_column_1,edt_table_row_4_column_2;
    Button btn_submit_item_shop, btn_upload_image;
    TextView txt_testing,txt_title_add_edit_item;
    String tempOfRandomKey;
    String Category;
    String image,imageEdit;
    RadioGroup list_opsi;
    ImageView img_uploadimage;
    private DatabaseReference databaseReference,reference;
    private FirebaseStorage storage;
    private StorageReference storageReference,storageRef;
    Uri imageUri;
    RadioButton plant, tools, seeds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_adding_item_shop);

        list_opsi = findViewById(R.id.opsi);
        edt_price = findViewById(R.id.edt_price);
        edt_weight = findViewById(R.id.edt_weight);
        edt_name_item = findViewById(R.id.edt_name_item);
        edt_height_of_plant = findViewById(R.id.edt_height_of_plant);
        edt_table_row_1_column_1 = findViewById(R.id.edt_table_row_1_column_1);
        edt_table_row_1_column_2 = findViewById(R.id.edt_table_row_1_column_2);
        edt_table_row_2_column_1 = findViewById(R.id.edt_table_row_2_column_1);
        edt_table_row_2_column_2 = findViewById(R.id.edt_table_row_2_column_2);
        edt_table_row_3_column_1 = findViewById(R.id.edt_table_row_3_column_1);
        edt_table_row_3_column_2 = findViewById(R.id.edt_table_row_3_column_2);
        edt_table_row_4_column_1 = findViewById(R.id.edt_table_row_4_column_1);
        edt_table_row_4_column_2 = findViewById(R.id.edt_table_row_4_column_2);
        btn_submit_item_shop = findViewById(R.id.btn_submit_item_shop);
        btn_upload_image = findViewById(R.id.btn_upload_image);

        img_uploadimage =findViewById(R.id.img_uploadimage);

        txt_testing = findViewById(R.id.txt_testinga);
        txt_title_add_edit_item = findViewById(R.id.txt_title_add_edit_item);

        plant = findViewById(R.id.plant);
        tools = findViewById(R.id.tools);
        seeds = findViewById(R.id.seeds);

        Intent intent = getIntent();
        final String IDItem = intent.getStringExtra("IDItem");
        Toast.makeText(getApplicationContext(), IDItem+"", Toast.LENGTH_SHORT).show();

        if(IDItem!= null){
            reference = FirebaseDatabase.getInstance().getReference().child("ShopItem").child(IDItem);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        txt_title_add_edit_item.setText("Edit Item Shop");
                        ShopModel p = snapshot.getValue(ShopModel.class);
                        Picasso.get().load(p.getImage()).into(img_uploadimage);
                        imageEdit = p.getImage();
                        String[] firstRow = p.getFirstRow().split("\\|");
                        String[] secondRow = p.getSecondRow().split("\\|");
                        String[] ThirdRow = p.getThirdRow().split("\\|");
                        String[] FourthRow = p.getFourthRow().split("\\|");

                        final String Category = p.getCategory();
                        final String NameItem = p.getNameItem();
                        final int Price = p.getPrice();
                        final int Weight = p.getWeight();

                        if(Category.equals("Plant")){
                            plant.setChecked(true);
                        }else if(Category.equals("Tools")){
                            tools.setChecked(true);
                        }else{
                            seeds.setChecked(true);
                        }

                        edt_name_item.setText(NameItem);
                        edt_height_of_plant.setText(p.getHeightOfPlant());
                        edt_price.setText(Price+"");
                        edt_weight.setText(Weight+"");

                        if(!p.getFirstRow().equals("|")){
                            edt_table_row_1_column_1.setText(firstRow[0]);
                            edt_table_row_1_column_2.setText(firstRow[1]);
                        }
                        if(!p.getSecondRow().equals("|")){
                            edt_table_row_2_column_1.setText(secondRow[0]);
                            edt_table_row_2_column_2.setText(secondRow[1]);
                        }
                        if(!p.getThirdRow().equals("|")){
                            edt_table_row_3_column_1.setText(ThirdRow[0]);
                            edt_table_row_3_column_2.setText(ThirdRow[1]);
                        }
                        if(!p.getFourthRow().equals("|")){
                            edt_table_row_4_column_1.setText(FourthRow[0]);
                            edt_table_row_4_column_2.setText(FourthRow[1]);
                        }

                    }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        list_opsi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.plant:
                        Category = "Plant";
                        edt_height_of_plant.setEnabled(true);
                        break;
                    case R.id.tools:
                        Category="Tools";
                        edt_height_of_plant.setText("");
                        edt_height_of_plant.setEnabled(false);
                        break;
                    case R.id.seeds:
                        Category="Seeds";
                        edt_height_of_plant.setText("");
                        edt_height_of_plant.setEnabled(false);
                        break;
                }
            }
        });

        img_uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        btn_upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPicture();
            }
        });


        btn_submit_item_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstRow = edt_table_row_1_column_1.getText().toString() +"|"+edt_table_row_1_column_2.getText().toString();
                String secondRow = edt_table_row_2_column_1.getText().toString() +"|"+edt_table_row_2_column_2.getText().toString();
                String thirdRow = edt_table_row_3_column_1.getText().toString() +"|"+edt_table_row_3_column_2.getText().toString();
                String fourthRow = edt_table_row_4_column_1.getText().toString() +"|"+edt_table_row_4_column_2.getText().toString();
                int price = Integer.parseInt(edt_price.getText().toString());
                int weight = Integer.parseInt(edt_weight.getText().toString());
                String nameOfItem = edt_name_item.getText().toString();
                String heightOfPlant = edt_height_of_plant.getText().toString();

                databaseReference = FirebaseDatabase.getInstance().getReference("ShopItem");
                final String keypush = databaseReference.push().getKey();
                ShopModel shopModel = new ShopModel();
                shopModel.setCategory(Category);
                shopModel.setHeightOfPlant(heightOfPlant);
                shopModel.setNameItem(nameOfItem);
                shopModel.setFirstRow(firstRow);
                shopModel.setSecondRow(secondRow);
                shopModel.setThirdRow(thirdRow);
                shopModel.setFourthRow(fourthRow);
                shopModel.setPrice(price);
                shopModel.setWeight(weight);
                if(IDItem != null){
                    shopModel.setIDItem(IDItem);
                    shopModel.setImage(imageEdit);
                    databaseReference.child(IDItem).setValue(shopModel);
                    Intent intent = new Intent(AdminAddingItemSHop.this, ListItemAdmin.class);
                    startActivity(intent);
                }else {
                    shopModel.setIDItem(keypush);
                    shopModel.setImage(image);
                    databaseReference.child(keypush).setValue(shopModel);
                    Intent intent = new Intent(AdminAddingItemSHop.this, AdminAddingItemSHop.class);
                    startActivity(intent);
                }

            }
        });

    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode==RESULT_OK&& data!=null &&data.getData()!=null){
            imageUri = data.getData();
            img_uploadimage.setImageURI(imageUri);
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image");
        pd.show();

        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/"+randomKey);
        tempOfRandomKey = randomKey;
        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"image uploaded", Toast.LENGTH_SHORT).show();
                        storageRef = FirebaseStorage.getInstance().getReference("images/"+randomKey);

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                image=uri.toString();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent+"%");
                    }
        });
    }
}
