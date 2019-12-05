package com.example.landandproperty4d.screen.viewinformationproperty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

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
import java.util.Collections;

public class ViewInformationProperty extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarViewInformationProperty ;
    private SearchView searchViewPost;
    private RecyclerView recyclerViewViewInformationproperty ;
    public static ArrayList<PostProperty> listpost = new ArrayList<>();
    private PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information_property);
        TakeDataPosts();
        init();

        toolbarViewInformationProperty.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchViewPost.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                postAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void init(){
        searchViewPost = findViewById(R.id.searchViewPost);
        toolbarViewInformationProperty = findViewById(R.id.toolBarViewInformationProperty);
        setSupportActionBar(toolbarViewInformationProperty);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewViewInformationproperty = findViewById(R.id.recyclerViewInformationProperty);
        recyclerViewViewInformationproperty.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerViewViewInformationproperty.addItemDecoration(dividerItemDecoration);
        recyclerViewViewInformationproperty.setLayoutManager(layoutManager);
    }

    public void TakeDataPosts(){
            mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                    for (DataSnapshot snapshot: nodeChild1) {
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            PostProperty post = postSnapshot.getValue(PostProperty.class);
                            post.setId(postSnapshot.getKey());
//                            Log.d("data", postSnapshot.getKey());
                            listpost.add(post);
                        }
                    }
                    postAdapter = new PostAdapter(listpost, getApplicationContext());
                    recyclerViewViewInformationproperty.setAdapter(postAdapter);
                    Collections.reverse(listpost);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}
