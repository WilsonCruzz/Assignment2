package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityFavoriteDetailsBinding;
import com.example.assignment2.model.MovieModel;
import com.example.assignment2.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FavoriteDetails extends AppCompatActivity {

    ActivityFavoriteDetailsBinding binding;

    FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();


        // get intent
        Intent intent = getIntent();
        String posterUrl = intent.getStringExtra("poster");
        String title = intent.getStringExtra("title");
        String year =  intent.getStringExtra("year");
        String type =  intent.getStringExtra("type");

        // setText
        binding.movieTitle.setText(title);
        binding.favMovieType.setText(type);


        // use Picasso to load image-
        Picasso.get()
                .load(posterUrl)
                .into(binding.moviePoster);

        binding.goBackBtn.setOnClickListener(view -> {
            finish();
        });
        binding.delFavBtn.setOnClickListener(view -> {
            removeMovieFromFavorites(mAuth.getCurrentUser().getUid(), new MovieModel(intent.getStringExtra("title")));
            finish();
        });
        binding.updateTypeBtn.setOnClickListener(view -> {
            String editedType = binding.favMovieType.getText().toString();
            // get the new type from edit text
            updateMoviesType(mAuth.getCurrentUser().getUid(), editedType);
        });

    }

    public void removeMovieFromFavorites(String uid, MovieModel movie) {
        DocumentReference userRef = db.collection("Users").document(uid);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                // User does not exist
                return;
            }
            // Get the user object from Firestore
            UserModel user = documentSnapshot.toObject(UserModel.class);
            List<MovieModel> favorites = user.getFavoriteMovies();

            if (favorites == null) {
                return;
            }
            Iterator<MovieModel> iterator = favorites.iterator();
            while (iterator.hasNext()) {
                MovieModel m = iterator.next();
                if (m.getTitle() != null && movie.getTitle() != null &&
                        m.getTitle().equalsIgnoreCase(movie.getTitle())) {
                    iterator.remove();
                    break;
                }
            }
            // Update the user's favorite movies in Firestore
            userRef.update("favoriteMovies", favorites);

        });

    }
    private void updateMoviesType(String uid, String type) {
        DocumentReference userRef = db.collection("Users").document(uid);
        // edit textview
        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                // User does not exist
                return;
                }
            // Get the user object from Firestore
            UserModel user = documentSnapshot.toObject(UserModel.class);
            List<MovieModel> favorites = user.getFavoriteMovies();
            if (favorites == null) {
                return;
                }
            Iterator<MovieModel> iterator = favorites.iterator();
            while (iterator.hasNext()) {
                MovieModel m = iterator.next();
                m.setType(type);
                }
            // Update the user's favorite movies in Firestore
            userRef.update("favoriteMovies", favorites);
        });


    }

}