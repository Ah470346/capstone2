package com.example.landandproperty4d.screen.viewinformationproperty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.Post;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.screen.postdetail.PostDetail;
import com.example.landandproperty4d.screen.postproperty.PostPropertyActivity;
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
    private RecyclerView recyclerViewViewInformationproperty ;
    public static ArrayList<PostProperty> listpost = new ArrayList<>();
    PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information_property);
        TakeDataPosts();
        init();

        toolbarViewInformationProperty.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent = new Intent(ViewInformationProperty.this, HomeActivity.class);
                        User user = dataSnapshot.getValue(User.class);
                        intent.putExtra("ruler",user.getRuler());
                        startActivity(intent);
                        finish();
                        postAdapter.clear();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void init(){

//        PostProperty a = new PostProperty();
//        a.setTitle("Đất Hot Cầu Rồng ");
//        a.setAddress("Thanh Khê, Đà Nẵng");
//        a.setPrice("100.000.000/m");
//        a.setPostDay("21/10/2019");
//
//        PostProperty b = new PostProperty();
//        b.setTitle("Đất Ngoại Ô Hòa Khánh Nam");
//        b.setAddress("Hòa Khánh Nam,Liên Chiểu, Đà Nẵng");
//        b.setPrice("50.000.000/m");
//        b.setPostDay("22/10/2019");
//
//        PostProperty c = new PostProperty();
//        c.setTitle("Đất Đất Nguyễn Văn Linh");
//        c.setAddress("15 Nguyễn Văn Linh,Hải Châu, Đà Nẵng");
//        c.setPrice("1.000.000.000/m");
//        c.setPostDay("22/10/2019");
//
//        listpost.add(a);
//        listpost.add(b);
//        listpost.add(c);

//        Log.d("aaa",a.getAddress());

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
