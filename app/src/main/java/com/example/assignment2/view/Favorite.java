package com.example.assignment2.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.assignment2.R;
import com.example.assignment2.databinding.ActivityFavoriteBinding;

public class Favorite extends AppCompatActivity {

    ActivityFavoriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchBtn.setOnClickListener(view -> {
            finish();
        });

    }
}