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
        String title = intent.getStringExtra("title");
        String year = intent.getStringExtra("year");
        String type = intent.getStringExtra("type");

        // setText
        String viewTitle = "Title :" + title;
        binding.movieTitle.setText(viewTitle);
        String viewYear = "Year :" + year;
        binding.movieYear.setText(viewYear);
        String viewType = "Type :" + type;
        binding.movieType.setText(viewType);


        // use Picasso to load image
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

    // get DB >>> User >> favoriteMovies>> check if movie exist  >> add movie >> update DB
    // This function checks if a movie already exists in the user’s favorite list. If it doesn’t,
    // it adds the movie and updates Firestore.
    public void addMovieToFavorites(String uid, MovieModel movie) {
        DocumentReference userRef = usersCollection.document(uid);

        userRef.get().addOnSuccessListener(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                // User does not exist
                Log.d("Firestore", "User not found");
                return;
            }
            // Get the user object from Firestore

            UserModel user = documentSnapshot.toObject(UserModel.class);
            List<MovieModel> favorites = user.getFavoriteMovies();

            if (favorites == null) {
                favorites = new ArrayList<>();
            }
            // Check if the movie already exists in the favorites list
            boolean alreadyExists = false;
            for (MovieModel m : favorites) {
                if (m.getTitle() != null && movie.getTitle() != null &&
                        m.getTitle().equalsIgnoreCase(movie.getTitle())) {
                    alreadyExists = true;
                    break;
                }
            }
            // Check if the movie already exists in the favorites list
            if (alreadyExists) {
                Log.d("Firestore", "Movie already in favorites");
                return;
            }
            favorites.add(movie);
            userRef.update("favoriteMovies", favorites);
        });
    }
}