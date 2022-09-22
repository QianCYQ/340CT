package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class QuantityCart extends AppCompatActivity {

    ImageView qtImage;
    TextView pdName, qtShow,qtPrice;
    Button btPlus, btMinus, btConfirm;
    ArrayList<CartInformation> mCartInfo;

    int count = 1;

    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quantity_cart);

        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userID=firebaseAuth.getCurrentUser().getUid();
        mCartInfo = new ArrayList<>();

        qtImage = findViewById(R.id.imageView);
        qtShow = findViewById(R.id.layoutQuantity);
        pdName = findViewById(R.id.layoutName);
        btConfirm = findViewById(R.id.layoutConfirm);
        btPlus = findViewById(R.id.addButton);
        btMinus = findViewById(R.id.minusButton);
        qtPrice = findViewById(R.id.layoutPrice);


        Intent i = getIntent();
        String product = i.getStringExtra("Product");
        String image = i.getStringExtra(("ImageURL"));
        String price = i.getStringExtra("Price");

        Picasso.get().load(image).into(qtImage);
        pdName.setText(product);
        qtPrice.setText("RM " + price);
        qtShow.setText(""+count);


        btPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                qtShow.setText(""+count);
            }
        });

        btMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<=1) count=1;
                else
                    count--;
                qtShow.setText(""+count);
            }
        });

        btConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String qty = qtShow.getText().toString();
                int qt1 = Integer.parseInt(qty);
                int p1 = Integer.parseInt(price);
                int totalPrice = qt1 * p1;

                Map<String, Object> Cart = new HashMap<>();
                Cart.put("ProductName",product);
                Cart.put("Price",price);
                Cart.put("TotalPrice",totalPrice + "");
                Cart.put("ImageURL",image);
                Cart.put("Quantity",qty);

                DocumentReference cartItem = fireStore.collection("Cart").document(userID)
                        .collection("Items").document();

                cartItem.set(Cart);
                Toast.makeText(view.getContext(),"Cart Updated", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(QuantityCart.this, Cart.class);
                startActivity(intent);

            }
        });

    }



}