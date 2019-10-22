package com.example.landandproperty4d.screen.postproperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.landandproperty4d.R;

public class LandContact extends AppCompatActivity {
    private Toolbar toolbarLandContact;
    private TextView txtLienHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_contact);
        init();

        toolbarLandContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandContact.this,PostPropertyActivity.class));
            }
        });
    }
    private void init(){
        toolbarLandContact = findViewById(R.id.toolBarLandContact);
        txtLienHe = findViewById(R.id.txtLienHe);
        txtLienHe.setPaintFlags(txtLienHe.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        setSupportActionBar(toolbarLandContact);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
