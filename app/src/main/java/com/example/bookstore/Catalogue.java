package com.example.bookstore;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Catalogue extends AppCompatActivity {

    private FirebaseFirestore fStore;

    private RecyclerView recyclerView;
    private ArrayList<BookInformation> mBookInformations;

    private CatalogueAdapter mCatalogueAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);

        fStore = FirebaseFirestore.getInstance();
        
        recyclerView = findViewById(R.id.RVCatalogue);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBookInformations = new ArrayList<>();

        mCatalogueAdapter = new CatalogueAdapter(mBookInformations, Catalogue.this);

        
        readBookInformationDB();

        recyclerView.setAdapter(mCatalogueAdapter);
    }

    private void readBookInformationDB() {

        fStore.collection("Books Information").orderBy("Title", Query.Direction.ASCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (int x=0; x<queryDocumentSnapshots.size(); x++){
                    DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(x);

                    String Title = documentSnapshot.getString("Title");
                    String ImageURL = documentSnapshot.getString("ImageURL");
                    String Description = documentSnapshot.getString("Description");
                    String Genre = documentSnapshot.getString("Category");
                    Double Price = documentSnapshot.getDouble("Cost");

                    BookInformation bookInformation = new BookInformation(Title,ImageURL,Description,Genre,Price);
                    mBookInformations.add(bookInformation);
                }
                mCatalogueAdapter.notifyDataSetChanged();
            }
        });

    }
}
