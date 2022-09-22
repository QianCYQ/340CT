package com.example.bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddBook extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private String mGetDocumentID, BookImageUrl;
    private FirebaseStorage storage;
    private StorageReference mStorageReference;
    private Uri mUri;

    private ImageView mAddBook_Image;
    private CardView mNewBookUploadImage, mNewBookAddBtn, mNewBookCancelBtn;
    private TextView mUploadingtxt;
    private EditText mNewBookTitle, mNewBookDescription, mNewBookCost, mNewBookCategory;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mGetDocumentID = fAuth.getCurrentUser().getUid();

        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReference();

        mAddBook_Image = findViewById(R.id.AddBook_Image);
        mNewBookUploadImage = findViewById(R.id.NewBookUploadImage);
        mNewBookAddBtn = findViewById(R.id.NewBookAddBtn);
        mNewBookCancelBtn = findViewById(R.id.NewBookCancelBtn);
        mNewBookTitle = findViewById(R.id.NewBookTitle);
        mNewBookDescription = findViewById(R.id.NewBookDescription);
        mNewBookCost = findViewById(R.id.NewBookCost);
        mNewBookCategory = findViewById(R.id.NewBookCategory);
        mUploadingtxt = findViewById(R.id.Uploadingtxt);

        mUploadingtxt.setVisibility(View.INVISIBLE);

        mNewBookUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });


        mNewBookAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = mNewBookTitle.getText().toString().trim();
                String Description = mNewBookDescription.getText().toString().trim();
                String Cost = mNewBookCost.getText().toString().trim();
                String Category = mNewBookCategory.getText().toString().trim();

                if(TextUtils.isEmpty(Title)){
                    mNewBookTitle.setError("This field cannot be empty.");
                    mNewBookTitle.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Description)){
                    mNewBookDescription.setError("This field cannot be empty.");
                    mNewBookDescription.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Cost)){
                    mNewBookCost.setError("This field cannot be empty.");
                    mNewBookCost.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(Category)){
                    mNewBookCategory.setError("This field cannot be empty.");
                    mNewBookCategory.requestFocus();
                    return;
                }

                if(mUri == null){
                    Toast.makeText(AddBook.this, "Please upload the picture of book.", Toast.LENGTH_SHORT).show();
                }

                mUploadingtxt.setVisibility(View.VISIBLE);
                mNewBookAddBtn.setVisibility(View.INVISIBLE);
                mNewBookCancelBtn.setVisibility(View.INVISIBLE);
//                UUID.randomUUID().toString()
                StorageReference storageReference = mStorageReference.child(Title);
                storageReference.putFile(mUri);

                Task<Uri> uriTask = storageReference.putFile(mUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return storageReference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            BookImageUrl = task.getResult().toString();

                            DocumentReference mAddNewBook = fStore.collection("Books Information").document();

                            Map<String, Object> mAddBook = new HashMap<>();
                            mAddBook.put("Title",Title);
                            mAddBook.put("Description",Description);
                            mAddBook.put("Cost",Cost);
                            mAddBook.put("Category",Category);
                            mAddBook.put("ImageURL",BookImageUrl);

                            mAddNewBook.set(mAddBook);
                            Toast.makeText(AddBook.this, "New book was uploaded.", Toast.LENGTH_SHORT).show();

                            finish();
                        }
                    }
                });





            }
        });

        mNewBookCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && data != null && data.getData() != null){
            mUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mUri);
                mAddBook_Image.setImageBitmap(bitmap);
                mNewBookUploadImage.setVisibility(View.INVISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
