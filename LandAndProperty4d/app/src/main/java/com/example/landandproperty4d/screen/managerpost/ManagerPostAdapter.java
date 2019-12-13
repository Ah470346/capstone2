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

public class ManagerPostAdapter extends RecyclerView.Adapter<ManagerPostAdapter.ViewHoler>{
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<PostProperty> listpost;
    ArrayList<String> listString;
    Context context;

    public ManagerPostAdapter(ArrayList<PostProperty> listpost, ArrayList<String> listString, Context context) {
        this.listpost = listpost;
        this.listString = listString;
        this.context = context;
    }
    @NonNull
    @Override
    public ManagerPostAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_manage_post_uuu,parent,false);
        return new ManagerPostAdapter.ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ManagerPostAdapter.ViewHoler holder, final int position) {
        holder.textViewTitleManagerPostU.setText(listpost.get(position).getTitle());
        holder.textViewAddressManagerPostU.setText(listpost.get(position).getAddress());
        holder.textViewPriceManagerPostU.setText(listpost.get(position).getPrice());
        holder.textViewPostDayManagerPostU.setText(listpost.get(position).getPostDay());
        Glide.with(context)
                .load(listString.get(position))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imageManagerPostU.setImageBitmap(resource);
                    }
                });

    }
    public void clear() {
        int size = listpost.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listpost.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
    @Override
    public int getItemCount() {
        return listpost.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder{
        TextView textViewTitleManagerPostU , textViewAddressManagerPostU,textViewPriceManagerPostU  , textViewPostDayManagerPostU,textViewDeleteU;
        private ImageView imageManagerPostU;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            textViewDeleteU = itemView.findViewById(R.id.textViewDeleteU);
            imageManagerPostU = itemView.findViewById(R.id.imageManagerPostU);
            textViewTitleManagerPostU = itemView.findViewById(R.id.textViewTitleManagerPostU);
            textViewAddressManagerPostU = itemView.findViewById(R.id.textViewAddressManagerPostU);
            textViewPriceManagerPostU = itemView.findViewById(R.id.textViewPriceManagerPostU);
            textViewPostDayManagerPostU = itemView.findViewById(R.id.textViewPostDayManagerPostU);
            textViewDeleteU.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                            for (DataSnapshot snapshot : nodeChild1) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    PostProperty post = postSnapshot.getValue(PostProperty.class);
                                    postSnapshot.getRef().removeValue();
                                    listpost.remove(getAdapterPosition());
                                    listString.remove(getAdapterPosition());
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
