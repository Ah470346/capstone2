package com.example.landandproperty4d.screen.postproperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.landandproperty4d.R;

public class LandLocation extends AppCompatActivity {
    private Toolbar toolbarLandLocation ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_location);
        init();

        toolbarLandLocation.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandLocation.this,PostPropertyActivity.class));
            }
        });
    }

    private void init(){
        toolbarLandLocation = findViewById(R.id.toolBarLandLocation);
        setSupportActionBar(toolbarLandLocation);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
