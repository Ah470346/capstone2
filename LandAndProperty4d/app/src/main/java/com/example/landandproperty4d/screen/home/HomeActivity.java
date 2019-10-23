package com.example.landandproperty4d.screen.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.screen.postproperty.PostPropertyActivity;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
import com.example.landandproperty4d.screen.viewmap4d.ViewMap4D;

public class HomeActivity extends AppCompatActivity {
    Button buttoLogout ,buttonPostProperty,buttonViewMap,buttonViewProperty;
    Toolbar toolbarHomePage ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();

        buttoLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonPostProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PostPropertyActivity.class);
                startActivity(intent);
            }
        });

        buttonViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewMap4D.class);
                startActivity(intent);
            }
        });

        buttonViewProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewInformationProperty.class);
                startActivity(intent);
            }
        });

    }

    public void init(){
        buttonViewMap = findViewById(R.id.buttonViewMap);
        buttoLogout = findViewById(R.id.buttonLogout);
        buttonPostProperty = findViewById(R.id.buttonPostProperty);
        buttonViewProperty = findViewById(R.id.buttonViewProperty);
        toolbarHomePage = findViewById(R.id.toolBarHomePage);
        setSupportActionBar(toolbarHomePage);
    }
}
