package com.example.landandproperty4d.screen.checkpost;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.ArrayList;
import java.util.Collections;

public class CheckPostActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarCheckPost;
    private TextView empty;
    private CatLoadingView progressBarCat;
    private RecyclerView recyclerCheckPost;
    private ArrayList<PostProperty> listpost = new ArrayList<>();
    private ArrayList<String> listString = new ArrayList<>();
    private CheckPostAdapter checkPostAdapter  ;
    private RecyclerView.AdapterDataObserver observer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_post);
        init();
        progressBarCat.show(getSupportFragmentManager(),"");
        TakeDataPosts();
        toolbarCheckPost.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){
        progressBarCat = new CatLoadingView();
        empty = findViewById(R.id.empty);
        empty.setVisibility(View.INVISIBLE);
        toolbarCheckPost = findViewById(R.id.toolBarCheckPost);
        recyclerCheckPost = findViewById(R.id.recyclerCheckPost);
        setSupportActionBar(toolbarCheckPost);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerCheckPost.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this ,RecyclerView.VERTICAL,false);
        DividerItemDecoration decoration = new DividerItemDecoration(this , manager.getOrientation());
        recyclerCheckPost.addItemDecoration(decoration);
        recyclerCheckPost.setLayoutManager(manager);
    }
    public void TakeDataPosts(){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild2 = dataSnapshot.getChildren();
                for (final DataSnapshot chilD: nodeChild2) {
                    final User u = chilD.getValue(User.class);
                    mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                            for (DataSnapshot snapshot: nodeChild1) {
                                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                    final PostProperty post = postSnapshot.getValue(PostProperty.class);
                                    post.setId(postSnapshot.getKey());
                                    if(chilD.getKey().equals(user.getUid())) {
                                        if (post.getCheck().contains(user.getUid())) {
                                            //
                                        } else {
                                            listpost.add(post);
                                            listString.add(LoadImage(post.getImagePost()));
                                        }
                                    }
                                }
                            }
                            if(chilD.getKey().equals(user.getUid())){
                                if(listpost.size()==0){
                                    empty.setVisibility(View.VISIBLE);
                                }
                            }
                            checkPostAdapter = new CheckPostAdapter(listpost,listString, getApplicationContext());
                            recyclerCheckPost.setAdapter(checkPostAdapter);
                            Collections.reverse(listpost);
                            Collections.reverse(listString);
                            progressBarCat.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
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

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
