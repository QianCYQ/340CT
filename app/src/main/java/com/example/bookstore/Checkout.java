package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Checkout extends AppCompatActivity {

    private FirebaseFirestore fStore;
    FirebaseAuth firebaseAuth;
    String userID, Address;

    TextView TotalPrice;
    EditText coAddress;
    Button coPlaceOrder;
    int FinalPrice;

    private RecyclerView recyclerView3;
    ArrayList<CheckoutInformation> mCheckoutInfo;

    private CheckoutAdapter mCheckoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        coAddress = findViewById(R.id.actAddress);
        coPlaceOrder = findViewById(R.id.actPlaceOrder);
        TotalPrice = findViewById(R.id.coTotalPrice);

        recyclerView3 = findViewById(R.id.RVCheckout);
        recyclerView3.setHasFixedSize(true);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this));
        mCheckoutInfo = new ArrayList<>();

        mCheckoutAdapter = new CheckoutAdapter(mCheckoutInfo, Checkout.this);

        readCheckoutInformationDB();

        recyclerView3.setAdapter(mCheckoutAdapter);

        coPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address = coAddress.getText().toString();

                if(TextUtils.isEmpty(Address)){
                    coAddress.setError("Please enter an address.");
                    coAddress.requestFocus();
                    return;
                }

                Map<String, Object> Order = new HashMap<>();
                Order.put("AddressOrder",Address);
                Order.put("TheOrder",mCheckoutInfo);
                Order.put("FinalPrice",FinalPrice);

                DocumentReference orderItem = fStore.collection("User Account Information").document(userID)
                        .collection("OrderPlaced").document();

                orderItem.set(Order);
                Toast.makeText(view.getContext(),"Order Placed", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), OrderPlaced.class));
                finish();
            }
        });

    }

    private void readCheckoutInformationDB() {

        fStore.collection("Cart").document(userID).collection("Items").orderBy("ProductName", Query.Direction.ASCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int x=0; x<queryDocumentSnapshots.size(); x++){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                            String Product = documentSnapshot.getString("ProductName");
                            String Price = documentSnapshot.getString("Price");
                            String Image = documentSnapshot.getString("ImageURL");
                            String Quantity = documentSnapshot.getString("Quantity");
                            String TotalPrice = documentSnapshot.getString("TotalPrice");

                            FinalPrice = FinalPrice + Integer.parseInt(TotalPrice);

                            CheckoutInformation CheckoutInformation = new CheckoutInformation(Product,Image,Price,Quantity,TotalPrice);
                            mCheckoutInfo.add(CheckoutInformation);
                        }
                        TotalPrice.setText("RM " + String.valueOf(FinalPrice));
                        mCheckoutAdapter.notifyDataSetChanged();
                    }
                });
    }



}