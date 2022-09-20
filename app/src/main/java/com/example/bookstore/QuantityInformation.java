package com.example.bookstore;

public class QuantityInformation {
    private String mBookTitle2, mBookImage2, mBookPrice2, mBookQuantity2;

    public QuantityInformation(String mBookTitle2, String mBookImage2, String mBookPrice2, String mBookQuantity2) {
        this.mBookTitle2 = mBookTitle2;
        this.mBookImage2 = mBookImage2;
        this.mBookPrice2 = mBookPrice2;
        this.mBookQuantity2 = mBookQuantity2;
    }

    public String getBookTitle2() { return mBookTitle2; }

    public void setBookTitle2(String BookTitle2) { mBookTitle2 = mBookTitle2; }

    public String getBookImage2() { return mBookImage2; }

    public void setBookImage2(String BookImage2) { mBookImage2 = mBookImage2; }

    public String getBookPrice2() { return mBookPrice2; }

    public void setBookPrice2(String BookPrice2) { mBookPrice2 = mBookPrice2; }

    public String getBookQuantity2() { return mBookQuantity2; }

    public void setBookQuantity2(String BookQuantity2) { mBookQuantity2 = mBookQuantity2; }
}
