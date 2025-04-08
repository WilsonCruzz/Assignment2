package com.example.assignment2.view;

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

        binding.goBackBtn.setOnClickListener(view -> {
            finish();
        });
        binding.DelFavBtn.setOnClickListener(view -> {
            //delete from firebase
        });
        binding.updateFavBtn.setOnClickListener(view -> {
            //update firebase
        });

    }
    public void addMovieToFavorites(String uid, MovieModel movie) {
        DocumentReference userRef = db.collection("users").document(uid);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                UserModel user = documentSnapshot.toObject(UserModel.class);
                List<MovieModel> favorites = user.getFavoriteMovies();

                if (favorites == null) {
                    favorites = new ArrayList<>();
                }

                // check if movie already exists
                boolean alreadyExists = false;
                for (MovieModel m : favorites) {
                    if (m.getTitle().equalsIgnoreCase(movie.getTitle())) {
                        alreadyExists = true;
                        break;
                    }
                }

                if (!alreadyExists) {
                    favorites.add(movie);
                    userRef.update("favoriteMovies", favorites);
                } else {
                    Log.d("Firestore", "Movie already in favorites");
                }
            }
        });
    }
    public void removeMovieFromFavorites(String uid, MovieModel movieToRemove) {
        DocumentReference userRef = db.collection("users").document(uid);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                UserModel user = documentSnapshot.toObject(UserModel.class);
                List<MovieModel> favorites = user.getFavoriteMovies();

                if (favorites != null) {
                    // use iterator to avoid ConcurrentModificationException
                    Iterator<MovieModel> iterator = favorites.iterator();
                    while (iterator.hasNext()) {
                        MovieModel m = iterator.next();
                        if (m.getTitle().equalsIgnoreCase(movieToRemove.getTitle())) {
                            iterator.remove();
                            break;
                        }
                    }

                    userRef.update("favoriteMovies", favorites);
                }
            }
        });
    }


}