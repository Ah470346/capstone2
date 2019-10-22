package com.example.landandproperty4d.screen.viewinformationproperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.screen.postproperty.PostPropertyActivity;

public class ViewInformationProperty extends AppCompatActivity {
    private Toolbar toolbarViewInformationProperty ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_information_property);
        init();

        toolbarViewInformationProperty.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewInformationProperty.this, PostPropertyActivity.class));
            }
        });
    }

    private void init(){
        toolbarViewInformationProperty = findViewById(R.id.toolBarViewInformationProperty);
        setSupportActionBar(toolbarViewInformationProperty);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
