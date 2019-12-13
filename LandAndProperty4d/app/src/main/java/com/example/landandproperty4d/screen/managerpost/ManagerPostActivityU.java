package com.example.landandproperty4d.screen.managerpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.http.PUT;

import android.os.Bundle;

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
import java.util.Collections;

public class ManagerPostActivityU extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarManagerPostU;
    private RecyclerView recyclerViewManagerPostU;
    private ArrayList<PostProperty> listpost = new ArrayList<>();
    private ArrayList<String> listString = new ArrayList<>();
    private ManagerPostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_post);
        init();
        TakeDataPost();
    }
    public void init(){
        toolbarManagerPostU = findViewById(R.id.toolBarManagerPostU);
        setSupportActionBar(toolbarManagerPostU);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewManagerPostU = findViewById(R.id.recyclerManagerPostU);
        recyclerViewManagerPostU.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerViewManagerPostU.addItemDecoration(dividerItemDecoration);
        recyclerViewManagerPostU.setLayoutManager(layoutManager);
    }
    public void TakeDataPost(){
        mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        final PostProperty post = postSnapshot.getValue(PostProperty.class);
                        if(snapshot.getKey().equals(user.getUid())) {
                            post.setId(postSnapshot.getKey());
                            listpost.add(post);
                            listString.add(LoadImage(post.getImagePost()));
                        }
                    }
                }
                postAdapter = new ManagerPostAdapter(listpost,listString, getApplicationContext());
                recyclerViewManagerPostU.setAdapter(postAdapter);
                Collections.reverse(listpost);
                Collections.reverse(listString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public String LoadImage( String a) {
        String b , c;
        b = a.replace("+","");
        c = b.replaceFirst(" ","");
        String[] arr = c.split(" ");
        return  arr[0];
    }
}
