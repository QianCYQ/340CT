package com.example.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;

    private String Username, Email, Password, PhoneNumber;

    private EditText mUsername, mPhoneNumber, mEmail, mPassword;
    private TextView mRegister, mBackToLogin;

    private String IDPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        mUsername = findViewById(R.id.RegisterUserName);
        mEmail = findViewById(R.id.RegisterEmail);
        mPhoneNumber = findViewById(R.id.RegisterPhone);
        mPassword = findViewById(R.id.RegisterPassword);

        mRegister = findViewById(R.id.RegisterButton);
        mBackToLogin = findViewById(R.id.BackToLoginButton);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Username = mUsername.getText().toString().trim();
                Email = mEmail.getText().toString().trim();
                PhoneNumber = mPhoneNumber.getText().toString().trim();
                Password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(Username)){
                    mUsername.setError("Please enter your username.");
                    mUsername.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Email)){
                    mEmail.setError("Please enter your email.");
                    mEmail.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(PhoneNumber)){
                    mPhoneNumber.setError("Please enter your phone number.");
                    mPhoneNumber.requestFocus();
                    return;
                }

                if(PhoneNumber.length() != 10){
                    mPhoneNumber.setError("Invalid format of phone number.");
                    mPhoneNumber.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Password)){
                    mPassword.setError("Please enter your password.");
                    mPassword.requestFocus();
                    return;
                }

                if(Password.length() < 8){
                    mPassword.setError("At least 8 character are required for password.");
                    mPassword.requestFocus();
                    return;
                }

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            IDPath = fAuth.getCurrentUser().getUid();
                            Toast.makeText(Register.this, "Account was registered successfully.", Toast.LENGTH_SHORT).show();

                            DocumentReference mdocumentReference = fStore.collection("User Account Information").document(IDPath);
                            Map<String, Object> RegisterInformation = new HashMap<>();

                            RegisterInformation.put("Username", Username);
                            RegisterInformation.put("Phone Number", PhoneNumber);
                            RegisterInformation.put("Email", Email);
                            RegisterInformation.put("Password", Password);

                            mdocumentReference.set(RegisterInformation);

                            Intent intent = new Intent(Register.this, Home.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Register.this, "Register failed. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
