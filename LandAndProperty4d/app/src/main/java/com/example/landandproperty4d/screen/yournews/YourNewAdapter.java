package com.example.landandproperty4d.screen.yournews;

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

public class YourNewAdapter extends RecyclerView.Adapter<YourNewAdapter.ViewHoler>{
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<PostProperty> listpost;
    ArrayList<String> listString;
    Context context;

    public YourNewAdapter(ArrayList<PostProperty> listpost,ArrayList<String> listString, Context context) {
        this.listpost = listpost;
        this.listString = listString;
        this.context = context;
    }
    @NonNull
    @Override
    public YourNewAdapter.ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_your_new_recyclerview,parent,false);
        return new YourNewAdapter.ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final YourNewAdapter.ViewHoler holder, final int position) {
        holder.textViewTitleYourNew.setText(listpost.get(position).getTitle());
        holder.textViewAddressYourNew.setText(listpost.get(position).getAddress());
        holder.textViewPriceYourNew.setText(listpost.get(position).getPrice());
        holder.textViewPostDayYourNew.setText(listpost.get(position).getPostDay());
        Glide.with(context)
                .load(listString.get(position))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imageViewYourNew.setImageBitmap(resource);
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
        TextView textViewTitleYourNew , textViewAddressYourNew,textViewPriceYourNew , textViewPostDayYourNew,empty;
        private ImageView imageViewYourNew,imageDelete;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imageDelete = itemView.findViewById(R.id.imageDelete);
            imageViewYourNew = itemView.findViewById(R.id.imageYourNew);
            textViewTitleYourNew = itemView.findViewById(R.id.textViewTitleYourNew);
            textViewAddressYourNew = itemView.findViewById(R.id.textViewAddressYourNew);
            textViewPriceYourNew = itemView.findViewById(R.id.textViewPriceYourNew);
            textViewPostDayYourNew = itemView.findViewById(R.id.textViewPostDayYourNew);

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                            for (DataSnapshot snapshot : nodeChild1) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    PostProperty post = postSnapshot.getValue(PostProperty.class);
                                    if (listpost.get(getAdapterPosition()).getId().equals(postSnapshot.getKey())) {
                                        postSnapshot.getRef().child("check").setValue(post.getCheck() + " " + user.getUid());
                                        listpost.remove(getAdapterPosition());
                                        notifyDataSetChanged();
                                        break;
                                    }
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
                    intent.putExtra("screen","view");
                    context.startActivity(intent);
                    Log.d("key",""+listpost.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
