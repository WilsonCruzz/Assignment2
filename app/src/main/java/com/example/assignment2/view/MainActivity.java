package com.example.assignment2.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment2.databinding.ActivityMainBinding;
import com.example.assignment2.viewmodel.StockViewModel;


public class MainActivity extends AppCompatActivity {

    StockViewModel viewModel;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        //viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel = new ViewModelProvider(this).get(StockViewModel.class);

        viewModel.getStockData().observe(this, stockData->{
            Log.i("tag","observe trigger");


            //TextView resultsTv = findViewById(R.id.resultsTv);

            binding.textView.setText(stockData.getUsdPrice());
            binding.textView2.setText(stockData.getJpyPrice());
            binding.textView3.setText(stockData.getEuroPrice());


        });

        //Button addOneBtn = findViewById(R.id.add1Btn);
        binding.refreshBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                viewModel.Refresh();
            }
        });
    }


}