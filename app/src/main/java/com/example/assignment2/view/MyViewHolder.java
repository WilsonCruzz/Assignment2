package com.example.assignment2.view;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView title, year;
    ItemClickListener itemClickListener;


    public MyViewHolder(@NonNull View itemView, ItemClickListener itemClickListener) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageview);
        title = itemView.findViewById(R.id.title_txt);
        year = itemView.findViewById(R.id.year_text);

        this.itemClickListener = itemClickListener;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                itemClickListener.onClick(view, getAdapterPosition());

            }
        });
    }
}
