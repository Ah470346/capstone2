package com.example.landandproperty4d.screen.postdetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    ArrayList<Bitmap> listDetail ;
    Context context;

    public DetailAdapter(ArrayList<Bitmap> listDetail, Context context) {
        this.listDetail = listDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custome_list_detail,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageDetail.setImageBitmap(listDetail.get(position));
    }

    @Override
    public int getItemCount() {
        return listDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageDetail ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDetail = itemView.findViewById(R.id.imageDetail);
        }
    }
}
