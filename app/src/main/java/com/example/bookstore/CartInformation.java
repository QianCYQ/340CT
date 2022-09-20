package com.example.bookstore;

public class CartInformation {
    private String mBookTitle, mBookImage, mBookPrice,mBookQuantity,mBookRemove, mTotalPrice;

    public CartInformation(String bookTitle, String bookImage, String bookPrice, String bookQuantity, String bookRemove, String bookTotalPrice) {
        mBookTitle = bookTitle;
        mBookImage = bookImage;
        mBookPrice = bookPrice;
        mBookQuantity = bookQuantity;
        mBookRemove = bookRemove;
        mTotalPrice = bookTotalPrice;

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

    public String getBookPrice() {
        return mBookPrice;
    }

    public void setBookPrice(String bookPrice) {
        mBookPrice = bookPrice;
    }

    public String getBookQuantity() {return mBookQuantity; }

    public void setBookQuantity(String bookQuantity) { mBookQuantity = bookQuantity; }

    public String getBookRemove() {return mBookRemove; }

    public void setBookRemove(String bookRemove) { mBookRemove = bookRemove; }

    public String getTotalPrice() {return mTotalPrice; }

    public void setTotalPrice(String bookTotalPrice) { mTotalPrice = bookTotalPrice; }

}
