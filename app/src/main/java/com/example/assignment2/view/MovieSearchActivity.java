package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMovieSearchBinding;
import com.example.assignment2.model.MovieModel;
import com.example.assignment2.viewmodel.MainActivityViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchActivity extends AppCompatActivity implements ItemClickListener {

    ActivityMovieSearchBinding binding;

    List<Item> itemList;
    MyAdapter myAdapter;
    MainActivityViewModel viewModel;

    FirebaseAuth mAuth;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        binding = ActivityMovieSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        itemList = new ArrayList<>();

        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        // Observe the LiveData
        viewModel.getMovieData().observe(this, movieList->{


            if (movieList != null) {
                // Clear the existing list
                itemList.clear();
                // Add the new movies to the list
                for (MovieModel movie : movieList) {
                    itemList.add(new Item(movie.getPoster(), movie.getTitle(), movie.getYear(), movie.getType()));
                }
            }


            // Set up the RecyclerView
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            // Set the layout manager
            binding.recyclerView.setLayoutManager(layoutManager);
            // Set the adapter
            myAdapter = new MyAdapter(getApplicationContext(), itemList);
            binding.recyclerView.setAdapter(myAdapter);

            // Set the item click listener
            myAdapter.setItemClickListener(this);

        });

        binding.searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                String title = binding.searchText.getText().toString();
                viewModel.Search(title);
            }
        });

        binding.GoToFavBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intentObj = new Intent(getApplicationContext(), Favorite.class);
                startActivity(intentObj);
            }
        });
    }


    @Override
    public void onClick(View v, int position) {
        Log.i("tag", "onClick trigger");
        Toast.makeText(this, "position = " + position, Toast.LENGTH_SHORT).show();

        Item selectedMovie = itemList.get(position);
        Intent intentObj = new Intent(MovieSearchActivity.this, MovieDetailsActivity.class);

        intentObj.putExtra("poster", selectedMovie.getItemImg());
        intentObj.putExtra("title", selectedMovie.getItemName());
        intentObj.putExtra("year", selectedMovie.getItemYear());
        intentObj.putExtra("type", selectedMovie.getItemType());

        startActivity(intentObj);

    }
}