package com.example.landandproperty4d.screen.notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.Collections;

public class NotifyActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarNotify;
    private RecyclerView recyclerNotify;
    private NotifyAdapter notifyAdapter;
    private TextView emptyNotify;
    private ArrayList<Notification> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        init();
        TakeDataNotify();
        toolbarNotify.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){
        emptyNotify = findViewById(R.id.emptyNotify);
        emptyNotify.setVisibility(View.INVISIBLE);
        toolbarNotify = findViewById(R.id.toolBarNotify);
        setSupportActionBar(toolbarNotify);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerNotify = findViewById(R.id.recyclerNotify);
        recyclerNotify.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(this , manager.getOrientation());
        recyclerNotify.addItemDecoration(decoration);
        recyclerNotify.setLayoutManager(manager);
    }

    private void TakeDataNotify(){
        mDatabase.child("Notification").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot: nodeChild) {
                    for (DataSnapshot snapshot :postSnapshot.getChildren()) {
                        Notification notification = snapshot.getValue(Notification.class);
                        if (notification.getIdSeller().equals(user.getUid())) {
                            notification.setId(snapshot.getKey());
                            list.add(notification);
                        }
                    }
                }
                if(list.size()==0){
                    emptyNotify.setVisibility(View.VISIBLE);
                }
                notifyAdapter = new NotifyAdapter(list,NotifyActivity.this);
                recyclerNotify.setAdapter(notifyAdapter);
                Collections.reverse(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
