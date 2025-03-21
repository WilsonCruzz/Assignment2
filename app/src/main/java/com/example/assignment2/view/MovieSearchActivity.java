package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMovieSearchBinding;

public class MovieSearchActivity extends AppCompatActivity {

    ActivityMovieSearchBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        binding = ActivityMovieSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent intentObj = new Intent(MovieSearchActivity.this, MovieDetailsActivity.class);
                startActivity(intentObj);

            }
        });



    }


}