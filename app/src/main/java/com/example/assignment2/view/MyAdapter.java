package com.example.assignment2.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Item> items;
    Context context;
    ItemClickListener itemClickListener;

    public MyAdapter(Context context,List<Item> items ) {
        this.items = items;
        this.context = context;
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    //1. Adapter calls onCreateViewHolder() → Creates a new ViewHolder instance.
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView, this.itemClickListener);

        return myViewHolder;
    }

    //Adapter calls onBindViewHolder() → Binds movie images and text to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d("Adapter", "onBindViewHolder: " + position);

        Item item = items.get(position);

        // Set the image using Picasso
        Picasso.get()
                .load(item.getItemImg())
                .into(holder.imageView);

        holder.title.setText(item.getItemName());
        holder.year.setText(item.getItemYear());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
