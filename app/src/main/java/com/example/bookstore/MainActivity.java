package com.example.bookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;

    private String Email, Password;

    private EditText mEmail, mPassword;
    private TextView mLogin, mGoToRegister, mForgotPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.UserEmail);
        mPassword = findViewById(R.id.UserPassword);

        mLogin = findViewById(R.id.LoginButton);
        mForgotPassword = findViewById(R.id.ForgotPassword);
        mGoToRegister = findViewById(R.id.GoToRegister);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email = mEmail.getText().toString().trim();
                Password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    mEmail.setError("Please enter your email.");
                    mEmail.requestFocus();
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

                fAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, Home.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Login failed. Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ResetPassword = new EditText(MainActivity.this);
                AlertDialog.Builder AlertDialogPassword = new AlertDialog.Builder(MainActivity.this);
                AlertDialogPassword.setTitle("Please enter your email address.");
                AlertDialogPassword.setView(ResetPassword);

                AlertDialogPassword.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String EmailForResetPassword = ResetPassword.getText().toString().trim();
                        fAuth.sendPasswordResetEmail(EmailForResetPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "A link was sent to your email address.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Please try again." , Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).setNegativeButton("Cancel", null);

                AlertDialogPassword.create().show();
            }
        });

        mGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }
}