package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityMovieSearchBinding;
import com.example.assignment2.model.MovieModel;
import com.example.assignment2.viewmodel.MainActivityViewModel;
import com.example.assignment2.viewmodel.StockViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchActivity extends AppCompatActivity implements ItemClickListener {

    ActivityMovieSearchBinding binding;

    List<Item> itemList;
    MyAdapter myAdapter;

    MainActivityViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);

        binding = ActivityMovieSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemList = new ArrayList<>();



        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.getMovieData().observe(this, movieList->{

            if (movieList != null) {
                itemList.clear();
                for (MovieModel movie : movieList) {
                    itemList.add(new Item(movie.getPoster(), movie.getTitle(), movie.getYear(), movie.getType()));
                }
            }


            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.recyclerView.setLayoutManager(layoutManager);
            myAdapter = new MyAdapter(getApplicationContext(), itemList);
            binding.recyclerView.setAdapter(myAdapter);

            myAdapter.setItemClickListener(this);


        });

        binding.searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                String title = binding.searchText.getText().toString();
                viewModel.Search(title);

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