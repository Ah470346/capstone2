package com.example.landandproperty4d.screen.viewnews;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.New;
import com.example.landandproperty4d.screen.newdetail.NewDetailActivity;
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

public class ViewNewActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarViewNew;
    private ImageView imageDemo;
    private LinearLayout layoutDemo;
    private TextView txtDemo,dayDemo;
    private CatLoadingView progressBarCat;
    private RecyclerView recyclerViewNew;
    private New new1;
    private ArrayList<New> list = new ArrayList<>();
    private NewAdapter newAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_new);
        init();
        TakeDataNew();

        toolbarViewNew.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layoutDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewNewActivity.this, NewDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("keynew",new1.getId());
                intent.putExtra("idAdmin",new1.getIdAdmin());
                startActivity(intent);
                Log.d("keynew",""+new1.getId());
            }
        });

    }

    public void init(){
        layoutDemo = findViewById(R.id.layoutDemo);
        imageDemo = findViewById(R.id.imageDemo);
        txtDemo = findViewById(R.id.txtDemo);
        dayDemo = findViewById(R.id.dayDemo);
        progressBarCat = new CatLoadingView();
        toolbarViewNew = findViewById(R.id.toolBarViewNew);
        setSupportActionBar(toolbarViewNew);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerViewNew = findViewById(R.id.recyclerViewNew);
        recyclerViewNew.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        recyclerViewNew.addItemDecoration(dividerItemDecoration);
        recyclerViewNew.setLayoutManager(layoutManager);
    }

    private void TakeDataNew (){
        progressBarCat.show(getSupportFragmentManager(),"");
        mDatabase.child("news").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        New news = postSnapshot.getValue(New.class);
                        news.setId(postSnapshot.getKey());
                        news.setIdAdmin(snapshot.getKey());
//                            Log.d("data", postSnapshot.getKey());
                        list.add(news);
                    }
                }
                Glide.with(getApplicationContext())
                        .load(list.get(0).getImage())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                imageDemo.setImageBitmap(resource);
                            }
                        });
                txtDemo.setText(list.get(0).getTitle());
                dayDemo.setText(list.get(0).getPostNewDay());
                new1 = list.get(0);
                list.remove(0);
                newAdapter = new NewAdapter(list, getApplicationContext());
                recyclerViewNew.setAdapter(newAdapter);
                Collections.reverse(list);
                progressBarCat.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
