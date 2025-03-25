package com.example.assignment2.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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


//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title1", "Description1"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title2", "Description2"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title3", "Description3"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title4", "Description4"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title5", "Description5"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title6", "Description6"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title7", "Description7"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title8", "Description8"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title9", "Description9"));
//        itemList.add(new Item(R.drawable.ic_launcher_background, "Title10", "Description10"));




        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.getMovieData().observe(this, movieList->{
            Log.i("tag", "observe trigger");

            if (movieList != null) {
                itemList.clear(); // 清除舊資料，避免重複顯示
                for (MovieModel movie : movieList) {
                    itemList.add(new Item(movie.getPoster(), movie.getTitle(), movie.getYear()));
                }
            }


            //TextView resultsTv = findViewById(R.id.resultsTv);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            binding.recyclerView.setLayoutManager(layoutManager);
            myAdapter = new MyAdapter(getApplicationContext(), itemList);
            binding.recyclerView.setAdapter(myAdapter);

            myAdapter.setItemClickListener(this);


        });


        binding.searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

//                Intent intentObj = new Intent(MovieSearchActivity.this, MovieDetailsActivity.class);
//                startActivity(intentObj);
                EditText searchText = findViewById(R.id.searchText);
                String title = searchText.getText().toString();

                viewModel.Search(title);

            }
        });



    }


    @Override
    public void onClick(View v, int position) {

    }
}