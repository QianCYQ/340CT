package com.example.bookstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    private ImageButton mSaveButton;
    private ImageView mEditProfileImage;
    private EditText mEditProfileName, mEditProfilePhoneNumber;
    private TextView mProfileEmail;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String userID;

    private static final int Image=1;
    private Uri imageUri;
    private String downloadUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        mSaveButton = findViewById(R.id.saveButton);
        mEditProfileImage = findViewById(R.id.editProfileImage);
        mEditProfileName = findViewById(R.id.editProfileName);
        mProfileEmail = findViewById(R.id.editProfileEmail);
        mEditProfilePhoneNumber = findViewById(R.id.editProfilePhoneNumber);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();

        storageReference= FirebaseStorage.getInstance().getReference().child("Images");

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEditProfileName2 = mEditProfileName.getText().toString();
                String mEditProfilePhoneNumber2 = mEditProfilePhoneNumber.getText().toString();

                if(mEditProfileName2.isEmpty()){
                    mEditProfileName.setError("Name required");
                    mEditProfileName.requestFocus();
                    return;
                }
                else if(mEditProfilePhoneNumber2.isEmpty()){
                    mEditProfilePhoneNumber.setError("Name required");
                    mEditProfilePhoneNumber.requestFocus();
                    return;
                }
                else {
                    DocumentReference documentReference = firebaseFirestore.collection("User Account Information").document(userID);
                    Map<String, Object> profileInfo = new HashMap<>();
                    profileInfo.put("Username", mEditProfileName.getText().toString());
                    profileInfo.put("Phone Number", mEditProfilePhoneNumber.getText().toString());
                    Toast.makeText(EditProfile.this, "Profile have been updated successfully!", Toast.LENGTH_LONG).show();
                    documentReference.update(profileInfo);

                    Intent intent = new Intent(getApplicationContext(), Profile.class);
                    startActivity(intent);
                }
            }
        });

        displayProfileEmail();
    }

    private void displayProfileEmail(){
        firebaseFirestore.collection("User Account Information").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String profileEmail = documentSnapshot.getString("Email");
                String profileName = documentSnapshot.getString("Username");
                String profilePhoneNumber = documentSnapshot.getString("Phone Number");
                String profileImage = documentSnapshot.getString("Image");

                Picasso.get().load(profileImage).into(mEditProfileImage);

                mEditProfileName.setText(String.valueOf(profileName));
                mEditProfilePhoneNumber.setText(String.valueOf(profilePhoneNumber));
                mProfileEmail.setText(String.valueOf(profileEmail));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}