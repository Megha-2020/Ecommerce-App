package com.example.ecommerceapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerceapp.Adapter.MyCartAdapter;
import com.example.ecommerceapp.Models.MyCartModel;
import com.example.ecommerceapp.Models.ShowAllModel;
import com.example.ecommerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView overAllAmount;
    RecyclerView recyclerView;
    List< MyCartModel > cartModelList;
    Button buyNow;
    MyCartAdapter cartAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    int totalBill;
    int overAllTotalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.my_cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buyNow = findViewById(R.id.buy_now);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //get data from My Cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(receiver,new IntentFilter("MyTotalAmount"));

        recyclerView = findViewById(R.id.cart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this,cartModelList);
        recyclerView.setAdapter(cartAdapter);
        overAllAmount = findViewById(R.id.textView2);

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,AddressActivity.class);
                i.putExtra("allAmount", String.valueOf(overAllAmount.getText()));
                startActivity(i);
            }
        });

        firestore.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("User").get().addOnCompleteListener(new OnCompleteListener< QuerySnapshot >() {
                    @Override
                    public void onComplete(@NonNull Task< QuerySnapshot > task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                MyCartModel myCartModel = document.toObject(MyCartModel.class);
                                cartModelList.add(myCartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalBill = intent.getIntExtra("totalAmount",0);
            overAllAmount.setText(String.valueOf(totalBill));

        }
    };
}