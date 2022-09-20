package com.example.bookstore;

public class CheckoutInformation {
    private String mBookTitle2, mBookImage2, mBookPrice2,mBookQuantity2, mTotalPrice2;


    public CheckoutInformation(String mBookTitle2, String mBookImage2, String mBookPrice2, String mBookQuantity2, String mTotalPrice2) {
        this.mBookTitle2 = mBookTitle2;
        this.mBookImage2 = mBookImage2;
        this.mBookPrice2 = mBookPrice2;
        this.mBookQuantity2 = mBookQuantity2;
        this.mTotalPrice2 = mTotalPrice2;
    }

    public String getBookTitle2() {
        return mBookTitle2;
    }

    public void setBookTitle2(String bookTitle2) {
        mBookTitle2 = bookTitle2;
    }

    public String getBookImage2() {
        return mBookImage2;
    }

    public void setBookImage2(String bookImage2) {
        mBookImage2 = bookImage2;
    }

    public String getBookPrice2() {
        return mBookPrice2;
    }

    public void setBookPrice2(String bookPrice2) {
        mBookPrice2 = bookPrice2;
    }

    public String getBookQuantity2() {return mBookQuantity2; }

    public void setBookQuantity2(String bookQuantity2) { mBookQuantity2 = bookQuantity2; }

    public String getTotalPrice2() {return mTotalPrice2; }

    public void setTotalPrice2(String bookTotalPrice2) { mTotalPrice2 = bookTotalPrice2; }



}
