package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ecommerceapp.Adapter.AddressAdapter;
import com.example.ecommerceapp.Models.AddressModel;
import com.example.ecommerceapp.Models.MyCartModel;
import com.example.ecommerceapp.Models.NewProductsModel;
import com.example.ecommerceapp.Models.PopularProductsModel;
import com.example.ecommerceapp.Models.ShowAllModel;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    Button addAddress,paymentBbtn;
    RecyclerView recyclerView;
    private List< AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    String setMyAddress = "";
    double amount = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        toolbar = findViewById(R.id.address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get data from Detailed Activity
        Object obj = getIntent().getSerializableExtra("item");
        String amt = getIntent().getStringExtra("allAmount");

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        paymentBbtn = findViewById(R.id.payment_btn);
        addAddress = findViewById(R.id.add_address_btn);
        recyclerView = findViewById(R.id.address_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        addressModelList = new ArrayList<>();
        addressAdapter = new AddressAdapter(getApplicationContext(),addressModelList,this);
        recyclerView.setAdapter(addressAdapter);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("Address").get().addOnCompleteListener(new OnCompleteListener< QuerySnapshot >() {
                    @Override
                    public void onComplete(@NonNull Task< QuerySnapshot > task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                AddressModel addressModel = document.toObject(AddressModel.class);
                                addressModelList.add(addressModel);
                                addressAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        paymentBbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (obj instanceof NewProductsModel){
                    NewProductsModel newProductsModel =(NewProductsModel) obj;
                    amount = newProductsModel.getPrice();
                }
                if (obj instanceof PopularProductsModel){
                    PopularProductsModel popularProductsModel =(PopularProductsModel) obj;
                    amount = popularProductsModel.getPrice();
                }
                if (obj instanceof ShowAllModel){
                    ShowAllModel showAllModel =(ShowAllModel) obj;
                    amount = showAllModel.getPrice();
                }else {
                    amount = Double.parseDouble(amt);
                }

                Intent intent = new Intent(AddressActivity.this,PaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
                finish();
            }
        });
    }

    @Override
    public void setAddress(String address) {

        setMyAddress = address;
    }
}