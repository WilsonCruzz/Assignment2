package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMovieDetailsBinding;
import com.example.assignment2.databinding.ActivityMovieSearchBinding;
import com.example.assignment2.model.MovieModel;
import com.example.assignment2.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {


    ActivityMovieDetailsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersCollection = db.collection("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get intent
        Intent intent = getIntent();
        String posterUrl = intent.getStringExtra("poster");
        String title = "Title: "+intent.getStringExtra("title");
        String year = "Year: "+intent.getStringExtra("year");
        String type = "Type: "+intent.getStringExtra("type");

        // setText
        binding.movieTitle.setText(title);
        binding.movieYear.setText(year);
        binding.movieType.setText(type);


        // use Picasso to load image-
        Picasso.get()
                .load(posterUrl)
                .into(binding.moviePoster);

        binding.goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.addFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovieToFavorites(FirebaseAuth.getInstance().getCurrentUser().getUid(), new MovieModel(posterUrl, title, year, type));
            }
        });

    }
    public void addMovieToFavorites(String uid, MovieModel movie) {
        DocumentReference userRef = usersCollection.document(uid);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                UserModel user = documentSnapshot.toObject(UserModel.class);
                List<MovieModel> favorites = user.getFavoriteMovies();

                if (favorites == null) {
                    favorites = new ArrayList<>();
                }

                boolean alreadyExists = false;
                for (MovieModel m : favorites) {
                    if (m.getTitle() != null && movie.getTitle() != null &&
                            m.getTitle().equalsIgnoreCase(movie.getTitle())) {
                        alreadyExists = true;
                        break;
                    }
                }


                if (!alreadyExists) {
                    favorites.add(movie);
                    userRef.update("favoriteMovies", favorites)
                            .addOnSuccessListener(aVoid -> Log.d("Firestore", "Movie added to favorites"))
                            .addOnFailureListener(e -> Log.w("Firestore", "Error updating favorites", e));
                } else {
                    Log.d("Firestore", "Movie already in favorites");
                }
            } else {
                Log.d("Firestore", "User not found");
            }
        }).addOnFailureListener(e -> Log.w("Firestore", "Error fetching user", e));
    }
}