package com.example.landandproperty4d.screen.checkpost;

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
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.screen.postdetail.PostDetail;
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

public class CheckPostAdapter extends RecyclerView.Adapter<CheckPostAdapter.ViewHolder> {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<PostProperty> listpost;
    ArrayList<String> listString;
    Context context;

    public CheckPostAdapter(ArrayList<PostProperty> listpost, ArrayList<String> listString, Context context) {
        this.listpost = listpost;
        this.listString = listString;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_check_post_recyclerview,parent,false);
        return new CheckPostAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.textViewTitleCheckPost.setText(listpost.get(position).getTitle());
        holder.textViewAddressCheckPost.setText(listpost.get(position).getAddress());
        holder.textViewPriceCheckPost.setText(listpost.get(position).getPrice());
        holder.textViewPostDayCheckPost.setText(listpost.get(position).getPostDay());
        Glide.with(context)
                .load(listString.get(position))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imageViewCheckPost.setImageBitmap(resource);
                    }
                });
    }
    @Override
    public int getItemCount() {
        return listpost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitleCheckPost , textViewAddressCheckPost,textViewPriceCheckPost, textViewPostDayCheckPost;
        private ImageView imageViewCheckPost,imageRemove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRemove = itemView.findViewById(R.id.imageRemove);
            imageViewCheckPost = itemView.findViewById(R.id.imageCheckPost);
            textViewTitleCheckPost = itemView.findViewById(R.id.textViewTitleCheckPost);
            textViewAddressCheckPost = itemView.findViewById(R.id.textViewAddressCheckPost);
            textViewPriceCheckPost = itemView.findViewById(R.id.textViewPriceCheckPost);
            textViewPostDayCheckPost = itemView.findViewById(R.id.textViewPostDayCheckPost);
            imageRemove.setOnClickListener(new View.OnClickListener() {
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("key",listpost.get(getAdapterPosition()).getId());
                    intent.putExtra("screen","check");
                    context.startActivity(intent);
                    Log.d("key",""+listpost.get(getAdapterPosition()).getId());
                }
            });
        }
    }

}
