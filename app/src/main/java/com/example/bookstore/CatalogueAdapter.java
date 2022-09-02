package com.example.bookstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.CatalogueHolder> {
    ArrayList<BookInformation> mBookInformation;
    Context mContext;

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

        holder.mBook_Title.setText(mBookInformation.get(position).getBookTitle());
        holder.mBook_Price.setText(String.valueOf(mBookInformation.get(position).getBookPrice()));
        holder.mBook_Genre.setText(mBookInformation.get(position).getBookGenre());
        holder.mBook_Description.setText(mBookInformation.get(position).getBookDescription());
    }


    @Override
    public int getItemCount() {
        return mBookInformation.size();
    }

    public static class CatalogueHolder extends RecyclerView.ViewHolder{
        private TextView mBook_Title, mBook_Price, mBook_Genre, mBook_Description;
        private ImageView mBook_Image;

        public CatalogueHolder(@NonNull View itemView) {
            super(itemView);

            mBook_Title = itemView.findViewById(R.id.BookTitle);
            mBook_Price = itemView.findViewById(R.id.BookPrice);
            mBook_Genre = itemView.findViewById(R.id.BookGenre);
            mBook_Description = itemView.findViewById(R.id.BookDescription);
            mBook_Image = itemView.findViewById(R.id.BookImage);

        }
    }


}
