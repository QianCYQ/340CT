package com.example.bookstore;

public class ProfileInformation {
    String mProfileImage, mProfileName, mProfileEmail, mProfilePhoneNumber;

    public ProfileInformation(String profileImage, String profileName, String profileEmail, String profilePhoneNumber){
        mProfileImage = profileImage;
        mProfileName = profileName;
        mProfileEmail = profileEmail;
        mProfilePhoneNumber = profilePhoneNumber;
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

    public String getProfilePhoneNumber(){
        return mProfilePhoneNumber;
    }

    public void setProfilePhoneNumber(String profilePhoneNumber) {
        mProfilePhoneNumber = profilePhoneNumber;
    }
}
