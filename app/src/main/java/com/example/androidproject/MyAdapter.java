package com.example.androidproject;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Score> list;

    public MyAdapter(Context context, ArrayList<Score> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
       return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        Score score = list.get(position);
        holder.score.setText(score.getScore());
        holder.date.setText(score.getDate());
        holder.statusImg.setImageResource(score.getStatusImg());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView score, date;
        ImageView statusImg;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            score = itemView.findViewById(R.id.tvScore);
            date = itemView.findViewById(R.id.tvDate);
            statusImg = itemView.findViewById(R.id.status_img);
        }
    }
}
