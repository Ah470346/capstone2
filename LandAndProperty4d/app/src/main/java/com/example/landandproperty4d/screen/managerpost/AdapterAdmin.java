package com.example.landandproperty4d.screen.managerpost;

import android.content.Context;
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
import com.example.landandproperty4d.data.model.PostProperty;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterAdmin extends RecyclerView.Adapter<AdapterAdmin.ViewHolder> {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<New> listnew;
    Context context;

    public AdapterAdmin(ArrayList<New> listnew, Context context) {
        this.listnew = listnew;
        this.context = context;

    }
    @NonNull
    @Override
    public AdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_manage_post_aaa,parent,false);
        return new AdapterAdmin.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterAdmin.ViewHolder holder, int position) {
        holder.textViewManagerPostA.setText(listnew.get(position).getTitle());
        holder.textViewManagerPostADay.setText(listnew.get(position).getPostNewDay());
        Glide.with(context)
                .load(listnew.get(position).getImage())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imageManagerPostA.setImageBitmap(resource);
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
        TextView textViewManagerPostA , textViewManagerPostADay,textViewDeleteA;
        ImageView imageManagerPostA;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewDeleteA = itemView.findViewById(R.id.textViewDeleteA);
            textViewManagerPostA = itemView.findViewById(R.id.textViewManagerPostATitle);
            textViewManagerPostADay = itemView.findViewById(R.id.textViewManagerPostADay);
            imageManagerPostA = itemView.findViewById(R.id.imageManagerPostA);
            textViewDeleteA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("news").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                            for (DataSnapshot snapshot : nodeChild1) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    New news = postSnapshot.getValue(New.class);
                                    postSnapshot.getRef().removeValue();
                                    listnew.remove(getAdapterPosition());
                                    notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            });
        }
    }
}