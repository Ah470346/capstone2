package com.example.landandproperty4d.screen.viewnews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.New;
import com.example.landandproperty4d.screen.postdetail.PostDetail;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewAdapter extends RecyclerView.Adapter<NewAdapter.ViewHolder> {
    private ArrayList<New> listnew;
    Context context;

    public NewAdapter(ArrayList<New> listnew, Context context) {
        this.listnew = listnew;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_recycler_new,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.textViewNewTitle.setText(listnew.get(position).getTitle());
        holder.textViewPostNewDay.setText(listnew.get(position).getPostNewDay());
        Glide.with(context)
                .load(listnew.get(position).getImage())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            holder.imageNew.setImageBitmap(resource);
                    }
                });
    }
    public void clear() {
        int size = listnew.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listnew.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
    @Override
    public int getItemCount() {
        return listnew.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNewTitle , textViewPostNewDay;
        ImageView imageNew;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNewTitle = itemView.findViewById(R.id.textViewNewTitle);
            textViewPostNewDay = itemView.findViewById(R.id.textViewPostNewDay);
            imageNew = itemView.findViewById(R.id.imageNew);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("keynew",listnew.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                    Log.d("keynew",""+listnew.get(getAdapterPosition()).getId());
                    clear();
                }
            });
        }
    }
}
