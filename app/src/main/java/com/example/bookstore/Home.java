package com.example.bookstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private CardView mBookCatalogueButton, mBookCategoryButton, mUserProfileButton, mLogoutButton, mUserCartButton, mAddBookButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mBookCatalogueButton = findViewById(R.id.BookCatalogueButton);
        mBookCategoryButton = findViewById(R.id.BookCategoryButton);
        mUserProfileButton = findViewById(R.id.UserProfileButton);
        mLogoutButton = findViewById(R.id.LogoutButton);
        mUserCartButton = findViewById(R.id.UserCartButton);
        mAddBookButton = findViewById(R.id.AddBookButton);

        mBookCatalogueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Catalogue.class);
                startActivity(intent);
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        mUserCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Cart.class));
                finish();
            }
        });

        mAddBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText EnterAccessPassword = new EditText(Home.this);
                AlertDialog.Builder AlertDialog = new AlertDialog.Builder(Home.this);
                AlertDialog.setTitle("Please enter the required password to access.");
                AlertDialog.setView(EnterAccessPassword);

                AlertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String AccessPassword = EnterAccessPassword.getText().toString().trim();
                        if (AccessPassword.equals("Hello123")){
                            Intent intent = new Intent(Home.this, AddBook.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Home.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                }).setNegativeButton("Cancel", null);

                AlertDialog.create().show();
            }
        });

    }
}
