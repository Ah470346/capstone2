package com.example.landandproperty4d.screen.viewinformationproperty;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.Post;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.screen.postdetail.PostDetail;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHoler>{
    ArrayList<PostProperty> listpost;
    private String key;
    private int p;
    Context context;

    public PostAdapter(ArrayList<PostProperty> listpost, Context context) {
        this.listpost = listpost;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_recyclerview_viewip,parent,false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.textViewTitle.setText(listpost.get(position).getTitle());
        holder.textViewAddress.setText(listpost.get(position).getAddress());
        holder.textViewPrice.setText(listpost.get(position).getPrice());
        holder.textViewTypeLand.setText(listpost.get(position).getTypeLand());
        holder.textViewPostDay.setText(listpost.get(position).getPostDay());

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
        TextView textViewTitle , textViewAddress,textViewPrice ,textViewTypeLand , textViewPostDay;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewTypeLand = itemView.findViewById(R.id.textViewTypeOfLand);
            textViewPostDay = itemView.findViewById(R.id.textViewPostDay);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PostDetail.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("key",listpost.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                    Log.d("key",""+listpost.get(getAdapterPosition()).getId());
                    clear();
                }
            });
        }
    }
}
