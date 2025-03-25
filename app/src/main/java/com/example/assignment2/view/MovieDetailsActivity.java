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
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {


    ActivityMovieDetailsBinding binding;

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

    }
}