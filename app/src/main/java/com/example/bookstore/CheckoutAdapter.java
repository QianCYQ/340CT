package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutHolder> {

    ArrayList<CheckoutInformation> mCheckoutInformation;
    Context mContext3;
    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    String userID;

    public CheckoutAdapter(ArrayList<CheckoutInformation> CheckoutInformation, Context context) {
        this.mCheckoutInformation = CheckoutInformation;
        this.mContext3 = context;
    }


    @NonNull
    @Override
    public CheckoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkoutadapter,parent,false);
        return new CheckoutAdapter.CheckoutHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CheckoutHolder holder, int position) {
        Picasso.get().load(mCheckoutInformation.get(position).getBookImage2()).into(holder.mBook_Image3);

        holder.mBook_Title3.setText(mCheckoutInformation.get(position).getBookTitle2());
        holder.mBook_TotalPrice3.setText(mCheckoutInformation.get(position).getTotalPrice2());
        holder.mBook_Quantity3.setText(mCheckoutInformation.get(position).getBookQuantity2());

        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userID=firebaseAuth.getCurrentUser().getUid();
    }


    @Override
    public int getItemCount() {
        return mCheckoutInformation.size();
    }

    public class CheckoutHolder extends RecyclerView.ViewHolder {
        private TextView mBook_Title3, mBook_TotalPrice3, mBook_Quantity3;
        private ImageView mBook_Image3;

        public CheckoutHolder(@NonNull View itemView) {
            super(itemView);
            mBook_Title3 = itemView.findViewById(R.id.coName);
            mBook_TotalPrice3 = itemView.findViewById(R.id.coTotalPrice);
            mBook_Quantity3 = itemView.findViewById(R.id.coQuantity);
            mBook_Image3 = itemView.findViewById(R.id.coImage);
        }
    }

}
