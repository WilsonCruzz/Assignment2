package com.example.assignment2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment2.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    MainActivityViewModel viewModel;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.getNumber().observe(this,getData->{
            Log.i("tag","observe trigger");


            //TextView resultsTv = findViewById(R.id.resultsTv);
            String strData = Integer.toString(getData);
            binding.resultsTv.setText(strData);

        });

        //Button addOneBtn = findViewById(R.id.add1Btn);
        binding.add1Btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                viewModel.addOne();
            }
        });
    }


}