package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    ArrayList<CartInformation> mCartInformation;
    Context mContext2;
    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    String userID;

    //pass arraylist
    public CartAdapter(ArrayList<CartInformation> CartInformation, Context context) {
        this.mCartInformation = CartInformation;
        this.mContext2 = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adapter,parent,false);
        return new CartAdapter.CartHolder(view);
    }

    //get
    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {

        Picasso.get().load(mCartInformation.get(position).getBookImage()).into(holder.mBook_Image2);

        holder.mBook_Title2.setText(mCartInformation.get(position).getBookTitle());
        holder.mBook_TotalPrice2.setText(mCartInformation.get(position).getTotalPrice());
        holder.mBook_Quantity2.setText(mCartInformation.get(position).getBookQuantity());

        String id = mCartInformation.get(position).getBookRemove();

        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userID=firebaseAuth.getCurrentUser().getUid();

        holder.mRemove_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireStore.collection("Cart").document(userID).collection("Items")
                        .document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(mContext2, "Book Deleted", Toast.LENGTH_SHORT).show();
                    }
                });

                mCartInformation.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), mCartInformation.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCartInformation.size();
    }

    public class CartHolder extends RecyclerView.ViewHolder {
        private TextView mBook_Title2, mBook_TotalPrice2, mBook_Quantity2;
        private ImageView mBook_Image2;
        private Button mRemove_Button;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            mBook_Title2 = itemView.findViewById(R.id.pdName);
            mBook_TotalPrice2 = itemView.findViewById(R.id.ttPrice);
            mBook_Quantity2 = itemView.findViewById(R.id.pdQuantity);
            mBook_Image2 = itemView.findViewById(R.id.pdImage);
            mRemove_Button = itemView.findViewById(R.id.pdRemove);

        }
    }
}
