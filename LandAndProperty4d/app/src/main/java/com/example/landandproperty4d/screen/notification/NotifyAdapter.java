package com.example.landandproperty4d.screen.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.Notification;
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

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.ViewHolder> {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<Notification> list ;
    Context context;

    public NotifyAdapter(ArrayList<Notification> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotifyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.custome_notification,parent,false);
        return new NotifyAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdapter.ViewHolder holder, int position) {
        holder.textViewPhone.setText(list.get(position).getPhone());
        holder.textViewPeople.setText(list.get(position).getInterestPeople());
        holder.textViewNamePost.setText(list.get(position).getNamePost());
        holder.textViewEmail.setText(list.get(position).getEmail());
        holder.textViewDayNotify.setText(list.get(position).getNotifyDay());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNamePost , textViewEmail,textViewPhone,textViewPeople,textViewDayNotify;
        private ImageView imageDeleteNotify;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageDeleteNotify = itemView.findViewById(R.id.imageDeleteNotify);
            textViewDayNotify = itemView.findViewById(R.id.textViewDayNotify);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewNamePost = itemView.findViewById(R.id.textViewNamePost);
            textViewPeople = itemView.findViewById(R.id.textViewPeople);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDatabase.child("Notification").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                            for (DataSnapshot snapshot : nodeChild1) { {
                                for (DataSnapshot postSnaphot : snapshot.getChildren()) {
                                    if (list.get(getAdapterPosition()).getId().equals(postSnaphot.getKey()))
                                        snapshot.getRef().removeValue();
                                    list.remove(getAdapterPosition());
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
        }
    }
}
