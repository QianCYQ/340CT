package com.example.bookstore;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    private ImageButton mEditButton;
    private FloatingActionButton mShoppingCartButton;
    private ImageView mProfileImage;
    private TextView mProfileName, mProfileEmail, mProfilePhoneNumber;
    private Button mUploadProfileImage;

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
        setContentView(R.layout.profile);

        mEditButton = findViewById(R.id.editButton);
        mShoppingCartButton = findViewById(R.id.shoppingCartButton);
        mUploadProfileImage = findViewById(R.id.uploadProfileImage);
        mProfileImage = findViewById(R.id.profileImage);
        mProfileName = findViewById(R.id.profileName);
        mProfileEmail = findViewById(R.id.profileEmail);
        mProfilePhoneNumber = findViewById(R.id.profilePhoneNumber);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userID = firebaseAuth.getCurrentUser().getUid();

        storageReference= FirebaseStorage.getInstance().getReference().child("Images");

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, EditProfile.class);
                startActivity(intent);
            }
        });

        mShoppingCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, Cart.class);
                startActivity(intent);
            }
        });

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmProfileImage();
            }
        });

        mUploadProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreImage();
            }
        });

        displayUserInformation();
    }

    private void displayUserInformation(){
        firebaseFirestore.collection("User Account Information").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String profileName = documentSnapshot.getString("Username");
                String profileEmail = documentSnapshot.getString("Email");
                String profilePhoneNumber = documentSnapshot.getString("Phone Number");
                String profileImage = documentSnapshot.getString("Image");

                Picasso.get().load(profileImage).into(mProfileImage);

                mProfileName.setText(String.valueOf(profileName));
                mProfileEmail.setText(String.valueOf(profileEmail));
                mProfilePhoneNumber.setText(String.valueOf(profilePhoneNumber));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setmProfileImage(){
        Intent image = new Intent();
        image.setAction(Intent.ACTION_GET_CONTENT);
        image.setType("image/*");
        startActivityForResult(image,Image);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Image && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            mProfileImage.setImageURI(imageUri);
        }
    }

    private void StoreImage(){
        Calendar mCalendar = Calendar.getInstance();

        SimpleDateFormat now = new SimpleDateFormat("MM-dd-yyyy");
        String currDate=now.format(mCalendar.getTime());

        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        String currTime=time.format(mCalendar.getTime());

        String imageID= currDate+currTime;

        StorageReference filePath= storageReference.child(imageUri.getLastPathSegment() + imageID+ ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(Profile.this, message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }

                        downloadUri=filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadUri=task.getResult().toString();
                            addImage();
                        }
                    }
                });
            }
        });
    }

    private void addImage(){
        DocumentReference documentReference = firebaseFirestore.collection("User Account Information").document(userID);
        Map<String, Object> AddImage= new HashMap<>();
        AddImage.put("Image", downloadUri);
        documentReference.update(AddImage);
    }
}