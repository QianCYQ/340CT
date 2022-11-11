package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Cart extends AppCompatActivity {

    Button coButton;
//  TextView smShow;

///   int position;
//    private int overTotalPrice = 0;

    private FirebaseFirestore fStore;

    private RecyclerView recyclerView2;
    ArrayList<CartInformation> mCartInfo;

    FirebaseAuth firebaseAuth;
    String userID;

    private CartAdapter mCartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        coButton = findViewById(R.id.ccButton);
//      smShow = findViewById(R.id.crtTotal);

        fStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        recyclerView2 = findViewById(R.id.RVCart);
        recyclerView2.setHasFixedSize(true);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        mCartInfo = new ArrayList<>();

        mCartAdapter = new CartAdapter(mCartInfo, Cart.this);

        readCartInformationDB();

        recyclerView2.setAdapter(mCartAdapter);

        coButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              startActivity(new Intent(getApplicationContext(), Checkout.class));
            }
        });

    }

    private void readCartInformationDB() {

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
                            String Remove = documentSnapshot.getId();
                            String TotalPrice = documentSnapshot.getString("TotalPrice");

                            CartInformation cartInformation = new CartInformation(Product,Image,Price,Quantity,Remove,TotalPrice);
                            mCartInfo.add(cartInformation);
                        }
                        mCartAdapter.notifyDataSetChanged();
                    }
                });

    }

}