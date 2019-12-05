package com.example.landandproperty4d.screen.postdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
import com.example.landandproperty4d.screen.viewmap4d.ViewMap4D;
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
import java.util.Timer;
import java.util.TimerTask;

public class PostDetail extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewTitleDetail ,textViewAddressDetail , textViewPriceDetail , textViewTypeOfLandDetail ,textViewPostDayDetail,
                    textViewAreaDetail ,textViewDirectionDetail,textViewContactDetail,textViewDetailOfDetail;
    private Toolbar toolbarPostDetail;
    private Button buttonDeal , buttonViewWithMap;
    private RecyclerView recyclerViewDetail;
    private CatLoadingView progressBarCat;
    private String polygonid;
    private DetailAdapter adapter;
    int i = 0;
    Timer timer;
    Handler handler;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        init();
        LoadDetail();
        toolbarPostDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostDetail.this, ViewInformationProperty.class));
            }
        });
    }

    public void init(){
        progressBarCat = new CatLoadingView();
        recyclerViewDetail = findViewById(R.id.recyclerViewDetail);
        toolbarPostDetail = findViewById(R.id.toolBarPostDetail);
        setSupportActionBar(toolbarPostDetail);
        buttonDeal = findViewById(R.id.buttonDeal);
        buttonViewWithMap = findViewById(R.id.buttonViewWithMap);
        textViewAddressDetail = findViewById(R.id.textViewAddressDetail);
        textViewAreaDetail = findViewById(R.id.textViewAreaDetail);
        textViewContactDetail = findViewById(R.id.textViewContactDetail);
        textViewDirectionDetail = findViewById(R.id.textViewDirectionDetail);
        textViewDetailOfDetail = findViewById(R.id.textViewDetailOfDetail);
        textViewPriceDetail = findViewById(R.id.textViewPriceDetail);
        textViewPostDayDetail = findViewById(R.id.textViewPostDayDetail);
        textViewTypeOfLandDetail = findViewById(R.id.textViewTypeOfLandDetail);
        textViewTitleDetail = findViewById(R.id.textViewTitleDetail);
        buttonDeal.setOnClickListener(this);
        buttonViewWithMap.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        recyclerViewDetail.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerViewDetail.addItemDecoration(dividerItemDecoration);
        recyclerViewDetail.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonDeal :
                break;
            case R.id.buttonViewWithMap :
                Intent intent = new Intent(PostDetail.this, ViewMap4D.class);
                intent.putExtra("maker",polygonid);
                startActivity(intent);
                break;
        }
    }

    public void LoadDetail(){
        progressBarCat.show(getSupportFragmentManager(),"");
        mDatabase.child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot: nodeChild) {
                    for (DataSnapshot snapshot: postSnapshot.getChildren()) {
                        PostProperty post = snapshot.getValue(PostProperty.class);
                        Intent intent = getIntent();
                        if(intent.getStringExtra("key").equals(snapshot.getKey())) {
                            textViewTitleDetail.setText(post.getTitle());
                            textViewAddressDetail.setText(post.getAddress());
                            textViewAreaDetail.setText(post.getArea());
                            textViewContactDetail.setText(post.getContact());
                            textViewDetailOfDetail.setText(post.getDetail());
                            textViewDirectionDetail.setText(post.getHouseDirection());
                            textViewPostDayDetail.setText(post.getPostDay());
                            textViewPriceDetail.setText(post.getPrice());
                            textViewTypeOfLandDetail.setText(post.getTypeLand());
                            LoadImage(post.getImagePost());
                            polygonid = post.getPolygonid();

                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void LoadImage( String a) {
        final ArrayList<Bitmap> list = new ArrayList<>();
        String b , c;
        b = a.replace("+","");
        c = b.replaceFirst(" ","");
        String[] arr = c.split(" ");
        for (String s : arr){
            Log.d("test",s);
            Glide.with(getApplicationContext())
                    .load(s)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            list.add(resource);
                            progressBarCat.dismiss();
                        }
                    });
        }
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                  Log.d("size", "" + list.size());
//                  adapter = new DetailAdapter(list, getApplicationContext());
//                  recyclerViewDetail.setAdapter(adapter);
//                  Collections.reverse(list);
//            }
//        },5000);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what < 5){
                    if(i <10 ){
                        i++;
                        Log.d("size", "" + list.size());
                        adapter = new DetailAdapter(list, getApplicationContext());
                        recyclerViewDetail.setAdapter(adapter);
                        Collections.reverse(list);
                    }

                }

            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000,1000);
    }

}
