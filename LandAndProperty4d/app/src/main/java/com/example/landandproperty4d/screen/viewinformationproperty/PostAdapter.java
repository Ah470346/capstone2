package com.example.landandproperty4d.screen.viewinformationproperty;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.screen.postdetail.PostDetail;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHoler> implements Filterable {
    ArrayList<PostProperty> listpost;
    ArrayList<String> listString;
    ArrayList<PostProperty> listpostfull;
    Context context;

    public PostAdapter(ArrayList<PostProperty> listpost,ArrayList<String> listString, Context context) {
        this.listpost = listpost;
        this.listString = listString;
        this.context = context;
        listpostfull = new ArrayList<>(listpost);
    }
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_recyclerview_viewip,parent,false);
        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoler holder, int position) {
        holder.textViewTitle.setText(listpost.get(position).getTitle());
        holder.textViewAddress.setText(listpost.get(position).getAddress());
        holder.textViewPrice.setText(listpost.get(position).getPrice());
        holder.textViewPostDay.setText(listpost.get(position).getPostDay());
        Glide.with(context)
                .load(listString.get(position))
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        holder.imageViewViewInformation.setImageBitmap(resource);
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
        TextView textViewTitle , textViewAddress,textViewPrice , textViewPostDay;
        private ImageView imageViewViewInformation;
        public ViewHoler(@NonNull View itemView) {
            super(itemView);
            imageViewViewInformation = itemView.findViewById(R.id.imageViewInformation);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewAddress = itemView.findViewById(R.id.textViewAddress);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewPostDay = itemView.findViewById(R.id.textViewPostDay);
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
    @Override
    public Filter getFilter() {
        return listFilter;
    }
    private  Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<PostProperty> filteredlist =  new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredlist.addAll(listpostfull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PostProperty item : listpostfull){
                    if(removeAccent(removePlace(item.getAddress())).toLowerCase().contains(removeAccent(removePlace(filterPattern)))){
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredlist;

            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
               listpost.clear();
               listpost.addAll((Collection<? extends PostProperty>) results.values);
               notifyDataSetChanged();
        }
    };

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }
    private String removePlace(String s){
        s.replace(" ", "");
        s.replace(",", "");
        return s;
        }
}
