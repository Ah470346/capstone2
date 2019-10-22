package com.example.landandproperty4d.screen.postproperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.landandproperty4d.R;

import java.util.ArrayList;

public class LandPrice extends AppCompatActivity {
    private Toolbar toolbarLandPrice ;
    private TextView txtDonVi,txtGia;
    private Spinner spinnerUnit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_price);
        init();

        toolbarLandPrice.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandPrice.this,PostPropertyActivity.class));
            }
        });
    }
    private void init(){
        txtDonVi = findViewById(R.id.txtDonVi);
        txtGia = findViewById(R.id.txtGia);
        txtGia.setPaintFlags(txtGia.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtDonVi.setPaintFlags(txtDonVi.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        ArrayList<String> listUnit = new ArrayList<>();
        listUnit.add("Triệu");
        listUnit.add("Tỷ");
        listUnit.add("Trăm Nghìn/m²");
        listUnit.add("Triệu/m²");
        listUnit.add("Tỷ/m²");
        listUnit.add("Thỏa Thuận");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listUnit);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapter);

        toolbarLandPrice = findViewById(R.id.toolBarLandPrice);
        setSupportActionBar(toolbarLandPrice);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
