package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.CatalogueHolder> {
    ArrayList<BookInformation> mBookInformation;
    Context mContext;
    FirebaseFirestore fireStore;
    FirebaseAuth firebaseAuth;
    String userID;

    public CatalogueAdapter(ArrayList<BookInformation> BookInformation, Context context) {
        this.mBookInformation = BookInformation;
        this.mContext = context;
    }

    @NonNull
    @Override
    public CatalogueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_adapter,parent,false);
        return new CatalogueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueHolder holder, int position) {
        Picasso.get().load(mBookInformation.get(position).getBookImage()).into(holder.mBook_Image);

        fireStore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userID=firebaseAuth.getCurrentUser().getUid();

        String bookTitle =mBookInformation.get(position).getBookTitle();
        String bookPrice =mBookInformation.get(position).getBookPrice();
        String bookImage =mBookInformation.get(position).getBookImage();

        holder.mBook_Title.setText(mBookInformation.get(position).getBookTitle());
        holder.mBook_Price.setText(mBookInformation.get(position).getBookPrice());
        holder.mBook_Genre.setText(mBookInformation.get(position).getBookGenre());
        holder.mBook_Description.setText(mBookInformation.get(position).getBookDescription());

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), QuantityCart.class);
                i.putExtra("Product", bookTitle);
                i.putExtra("ImageURL", bookImage);
                i.putExtra("Price", bookPrice);
                view.getContext().startActivity(i);

//                Map<String, Object> Cart = new HashMap<>();
//                Cart.put("ProductName",bookTitle);
//                Cart.put("Price",bookPrice);
//                Cart.put("ImageURL",bookImage);
//
//                DocumentReference cartItem = fireStore.collection("Cart").document(userID)
//                        .collection("Items").document();
//
//                cartItem.set(Cart);
//                        Toast.makeText(view.getContext(),"Cart Updated", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(mContext, Cart.class);
//                        mContext.startActivity(intent);

///               fireStore.collection("Cart").document(userID).co(Cart).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(view.getContext(),"Cart Updated", Toast.LENGTH_LONG).show();
//                        Intent intent = new Intent(mContext, Cart.class);
//                        mContext.startActivity(intent);
//                    }
//                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBookInformation.size();
    }

    public static class CatalogueHolder extends RecyclerView.ViewHolder{
        private TextView mBook_Title, mBook_Price, mBook_Genre, mBook_Description, addToCart;
        private ImageView mBook_Image;

        public CatalogueHolder(@NonNull View itemView) {
            super(itemView);

            mBook_Title = itemView.findViewById(R.id.BookTitle);
            mBook_Price = itemView.findViewById(R.id.BookPrice);
            mBook_Genre = itemView.findViewById(R.id.BookGenre);
            mBook_Description = itemView.findViewById(R.id.BookDescription);
            mBook_Image = itemView.findViewById(R.id.BookImage);
            addToCart = itemView.findViewById(R.id.AddToCartButton);

        }
    }


}
