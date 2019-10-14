package com.example.landandproperty4d.screen.postproperty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.landandproperty4d.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>  {
    ArrayList<RecyclerViewData> listimage;
    Context context;

    public ImageAdapter(ArrayList<RecyclerViewData> listimage, Context context) {
        this.listimage = listimage;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_recyclerview,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.imageViewLandImage.setImageBitmap(listimage.get(position).getImage());
        holder.textViewRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Remove(position);
            }
        });

    }

    private void Remove (int position){
        listimage.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listimage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewLandImage;
        TextView textViewRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewLandImage = itemView.findViewById(R.id.imageViewLandImage);
            textViewRemove = itemView.findViewById(R.id.textViewRemove);
        }
    }
}
