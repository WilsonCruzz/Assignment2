package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.assignment2.databinding.ActivityFavoriteBinding;
import com.example.assignment2.model.MovieModel;
import com.example.assignment2.model.UserModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;
import java.util.List;

public class Favorite extends AppCompatActivity implements ItemClickListener {

    ActivityFavoriteBinding binding;

    List<Item> itemList;
    MyAdapter myAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollection = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemList = new ArrayList<>();

        // Set RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(Favorite.this);
        binding.favRecyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter(Favorite.this, itemList);
        binding.favRecyclerView.setAdapter(myAdapter);

        //no longer using LiveData to store data
        GetFavMovie();

        binding.searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Set the item click listener
        myAdapter.setItemClickListener(this);

    }

    // This function retrieves the current user’s favorite movies from Firestore,
    // converts them into Item objects, and updates the UI list.
    private void GetFavMovie() {

        // Get the current user's UID
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Fetch the user document from Firestore
        usersCollection.document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (!documentSnapshot.exists()) {
                    Log.d("Firestore", "User not found");
                    return;
                }
                // Get the user object from Firestore
                UserModel user = documentSnapshot.toObject(UserModel.class);
                // Check if the user has favorite movies
                List<MovieModel> favorites = user.getFavoriteMovies();
                itemList.clear();
                // Add movies to the item list
                for (MovieModel movie : favorites) {
                    Item item = new Item(
                            movie.getPoster(),
                            movie.getTitle(),
                            movie.getYear(),
                            movie.getType()
                    );
                    Log.d("Favorite", "Item: " + item.getItemName());
                    itemList.add(item);
                    Log.d("Favorite", "Item list size: " + itemList.size());
                }
                // notify the adapter that the data has changed
                myAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v, int position) {
        Log.i("tag", "onClick trigger");
        Toast.makeText(Favorite.this, "position = " + position, Toast.LENGTH_SHORT).show();

        Item selectedMovie = itemList.get(position);
        Intent intentObj = new Intent(Favorite.this, FavoriteDetails.class);

        intentObj.putExtra("poster", selectedMovie.getItemImg());
        intentObj.putExtra("title", selectedMovie.getItemName());
        intentObj.putExtra("year", selectedMovie.getItemYear());
        intentObj.putExtra("type", selectedMovie.getItemType());

        startActivity(intentObj);
    }
}