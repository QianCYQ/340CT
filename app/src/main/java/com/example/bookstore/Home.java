package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private CardView mBookCatalogueButton, mBookCategoryButton, mUserProfileButton, mLogoutButton;

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
                fAuth.signOut();
                finish();
            }
        });

    }
}
