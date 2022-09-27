package com.example.bookstore;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EditProfile extends AppCompatActivity {
    private ImageButton mSaveButton;
    private ImageView mEditProfileImage;
    private TextView mEditProfileName;
    private TextView mEditProfileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        mSaveButton = findViewById(R.id.saveButton);
        mEditProfileImage = findViewById(R.id.editProfileImage);
        mEditProfileName = findViewById(R.id.editProfileName);
        mEditProfileEmail = findViewById(R.id.editProfileEmail);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String mEditProfileImage2 = mEditProfileImage.getImage().toString();
                String mEditProfileName2 = mEditProfileName.getText().toString();
                String mEditProfileEmail2 = mEditProfileEmail.getText().toString();
            }
        });
    }
}