package com.example.landandproperty4d.screen.viewmap4d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import vn.map4d.map4dsdk.maps.MFSupportMapFragment;
import vn.map4d.map4dsdk.maps.Map4D;
import vn.map4d.map4dsdk.maps.OnMapReadyCallback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.screen.home.HomeActivity;

public class ViewMap4D extends AppCompatActivity implements OnMapReadyCallback {
    Toolbar toolbarViewMap4D ;
    private MFSupportMapFragment fragmentMap4d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map4_d);
        init();

        toolbarViewMap4D.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewMap4D.this, HomeActivity.class));
            }
        });
    }

    private void init(){
        toolbarViewMap4D = findViewById(R.id.toolBarViewMap4D);
        setSupportActionBar(toolbarViewMap4D);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentMap4d = (MFSupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3D);
        fragmentMap4d.getMapAsync( ViewMap4D.this);



    }

    @Override
    public void onMapReady(Map4D map4D) {
        map4D.setMinZoomPreference(17.f);
        map4D.enable3DMode(true);
    }
}
