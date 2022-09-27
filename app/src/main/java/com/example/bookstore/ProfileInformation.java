package com.example.bookstore;

public class ProfileInformation {
    String mProfileImage, mProfileName, mProfileEmail;

    public ProfileInformation(String profileImage, String profileName, String profileEmail){
        mProfileImage = profileImage;
        mProfileName = profileName;
        mProfileEmail = profileEmail;
    }

    public String getProfileImage(){
        return mProfileImage;
    }

    public void setProfileImage(String profileImage) {
        mProfileImage = profileImage;
    }

    public String getProfileName(){
        return mProfileName;
    }

    public void setProfileName(String profileName) {
        mProfileName = profileName;
    }

    public String getProfileEmail(){
        return mProfileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        mProfileEmail = profileEmail;
    }
}
