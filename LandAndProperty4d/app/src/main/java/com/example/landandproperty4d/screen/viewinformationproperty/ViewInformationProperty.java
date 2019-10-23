package com.example.landandproperty4d.screen.viewinformationproperty;

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
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.postproperty.PostPropertyActivity;

import java.util.ArrayList;

public class ViewInformationProperty extends AppCompatActivity {
    private Toolbar toolbarViewInformationProperty ;
    private RecyclerView recyclerViewViewInformationproperty ;
    ArrayList<Post> listpost = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information_property);
        init();

        toolbarViewInformationProperty.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewInformationProperty.this, HomeActivity.class));
            }
        });
    }

    private void init(){

        Post a = new Post();
        a.setTitle("Đất Hót Cầu Rồng ");
        a.setAddress("Thanh Khê, Đà Nẵng");
        a.setPrice("100.000.000/m");
        a.setPostDay("21/10/2019");

        Post b = new Post();
        b.setTitle("Đất Ngoại Ô Hòa Khánh Nam");
        b.setAddress("Hòa Khánh Nam,Liên Chiểu, Đà Nẵng");
        b.setPrice("50.000.000/m");
        b.setPostDay("22/10/2019");

        Post c = new Post();
        c.setTitle("Đất Đất Nguyễn Văn Linh");
        c.setAddress("15 Nguyễn Văn Linh,Hải Châu, Đà Nẵng");
        c.setPrice("1.000.000.000/m");
        c.setPostDay("22/10/2019");

        listpost.add(a);
        listpost.add(b);
        listpost.add(c);

        Log.d("aaa",a.getAddress());

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
        PostAdapter postAdapter = new PostAdapter(listpost, getApplicationContext());
        recyclerViewViewInformationproperty.setAdapter(postAdapter);
    }
}
