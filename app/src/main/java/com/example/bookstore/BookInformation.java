package com.example.bookstore;

public class BookInformation {
    private String mBookTitle, mBookImage, mBookDescription, mBookGenre;
    private double mBookPrice;


    public BookInformation(String bookTitle, String bookImage, String bookDescription, String bookGenre, double bookPrice) {
        mBookTitle = bookTitle;
        mBookImage = bookImage;
        mBookDescription = bookDescription;
        mBookGenre = bookGenre;
        mBookPrice = bookPrice;
    }

    public String getBookTitle() {
        return mBookTitle;
    }

    public void setBookTitle(String bookTitle) {
        mBookTitle = bookTitle;
    }

    public String getBookImage() {
        return mBookImage;
    }

    public void setBookImage(String bookImage) {
        mBookImage = bookImage;
    }

    public String getBookDescription() {
        return mBookDescription;
    }

    public void setBookDescription(String bookDescription) {
        mBookDescription = bookDescription;
    }

    public String getBookGenre() {
        return mBookGenre;
    }

    public void setBookGenre(String bookGenre) {
        mBookGenre = bookGenre;
    }

    public double getBookPrice() {
        return mBookPrice;
    }

    public void setBookPrice(double bookPrice) {
        mBookPrice = bookPrice;
    }
}
