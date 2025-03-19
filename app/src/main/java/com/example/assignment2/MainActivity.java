package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;


public class MainActivity extends AppCompatActivity {

    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        TextView resultsTv = findViewById(R.id.resultsTv);
        resultsTv.setText(String.valueOf(viewModel.getNumber()));

        Button addOneBtn = findViewById(R.id.add1Btn);
        addOneBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                viewModel.addOne();
                resultsTv.setText(String.valueOf(viewModel.getNumber()));

            }
        });
    }


}