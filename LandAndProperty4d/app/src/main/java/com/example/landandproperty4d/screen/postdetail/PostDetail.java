package com.example.landandproperty4d.screen.postdetail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.Post;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
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
import java.util.concurrent.ExecutionException;

public class PostDetail extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewTitleDetail ,textViewAddressDetail , textViewPriceDetail , textViewTypeOfLandDetail ,textViewPostDayDetail,
                    textViewAreaDetail,textViewLocationDetail ,textViewDirectionDetail,textViewContactDetail,textViewDetailOfDetail;
    private Toolbar toolbarPostDetail;
    private Button buttonDeal , buttonViewWithMap;
    private RecyclerView recyclerViewDetail;
    private DetailAdapter adapter;
    private ArrayList<Bitmap> list = new ArrayList<>();
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
        textViewLocationDetail = findViewById(R.id.textViewLocationDetail);
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
                break;
        }
    }

    public void LoadDetail(){
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
                            textViewLocationDetail.setText(post.getLocation());
                            textViewPriceDetail.setText(post.getPrice());
                            textViewTypeOfLandDetail.setText(post.getTypeLand());
                            LoadImage(post.getImagePost());
                        }
                    }
                }
                adapter = new DetailAdapter(list, getApplicationContext());
                recyclerViewDetail.setAdapter(adapter);
                Collections.reverse(list);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void LoadImage( String a) {
        String b , c;
        b = a.replace("+","");
        c = b.replaceFirst(" ","");
        String[] arr = c.split(" ");
        for (String s : arr){
            try {
                Bitmap bitmap =Glide.with(this)
                               .load(s)
                               .asBitmap()
                               .into(100,100)
                               .get();
                list.add(bitmap);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("test",s);

        }
    }
}
