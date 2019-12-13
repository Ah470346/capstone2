package com.example.landandproperty4d.screen.managerpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.New;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ManagerPostActivityA extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarManagerPostA;
    private RecyclerView recyclerViewManagerPostA;
    private ArrayList<New> list = new ArrayList<>();
    private AdapterAdmin adapterAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_post_activity);
        init();
        TakeDataNew();
    }
    public void init(){
        toolbarManagerPostA = findViewById(R.id.toolBarManagerPostA);
        setSupportActionBar(toolbarManagerPostA);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewManagerPostA = findViewById(R.id.recyclerManagerPostA);
        recyclerViewManagerPostA.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerViewManagerPostA.addItemDecoration(dividerItemDecoration);
        recyclerViewManagerPostA.setLayoutManager(layoutManager);
    }
    private void TakeDataNew () {
        mDatabase.child("news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        final New news = postSnapshot.getValue(New.class);
                        if(snapshot.getKey().equals(user.getUid())) {
                            news.setId(postSnapshot.getKey());
                            list.add(news);
                        }
                    }
                }
                adapterAdmin = new AdapterAdmin(list, getApplicationContext());
                recyclerViewManagerPostA.setAdapter(adapterAdmin);
                Collections.reverse(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
