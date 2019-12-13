package com.example.landandproperty4d.screen.newdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.New;
import com.example.landandproperty4d.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.Timer;

public class NewDetailActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView textViewDayNewDetail,textViewContentDetail , textViewNewTitleDetail,textViewAuthor;
    private ImageView imageViewNewDetail;
    private Toolbar toolbarNewDetail ;
    private CatLoadingView progressBarCat;
    private Timer timer;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail);
        init();
        LoadDetail();
        LoadCre();

        toolbarNewDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void init(){
        progressBarCat = new CatLoadingView();
        toolbarNewDetail = findViewById(R.id.toolBarNewDetail);
        setSupportActionBar(toolbarNewDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        textViewContentDetail = findViewById(R.id.textViewContentDetail);
        textViewDayNewDetail = findViewById(R.id.textViewDayNewDetail);
        textViewNewTitleDetail = findViewById(R.id.textViewNewTitleDetail);
        imageViewNewDetail = findViewById(R.id.imageViewNewDetail);
    }

    public void LoadDetail(){
        progressBarCat.show(getSupportFragmentManager(),"");
        mDatabase.child("news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot: nodeChild) {
                    for (DataSnapshot snapshot: postSnapshot.getChildren()) {
                        New news = snapshot.getValue(New.class);
                        Intent intent = getIntent();
                        if(intent.getStringExtra("keynew").equals(snapshot.getKey())) {
                            textViewContentDetail.setText(news.getContent());
                            textViewDayNewDetail.setText(news.getPostNewDay());
                            textViewNewTitleDetail.setText(news.getTitle());
                            Glide.with(NewDetailActivity.this)
                                    .load(news.getImage())
                                    .asBitmap()
                                    .into(new SimpleTarget<Bitmap>() {
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            imageViewNewDetail.setImageBitmap(resource);
                                        }
                                    });
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
    public void LoadCre(){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot : nodeChild) {
                    User user = postSnapshot.getValue(User.class);
                    Intent intent = getIntent();
                    if (intent.getStringExtra("idAdmin").equals(postSnapshot.getKey())) {
                        textViewAuthor.setText("Cre: "+user.getName());
                        progressBarCat.dismiss();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
