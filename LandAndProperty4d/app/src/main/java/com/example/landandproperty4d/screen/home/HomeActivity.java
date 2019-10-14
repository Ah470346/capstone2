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

public class HomeActivity extends AppCompatActivity {
    Button buttoLogout ,buttonPostProperty;
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


    }

    public void init(){
        buttoLogout = findViewById(R.id.buttonLogout);
        buttonPostProperty = findViewById(R.id.buttonPostProperty);
        toolbarHomePage = findViewById(R.id.toolBarHomePage);
        setSupportActionBar(toolbarHomePage);
    }
}
