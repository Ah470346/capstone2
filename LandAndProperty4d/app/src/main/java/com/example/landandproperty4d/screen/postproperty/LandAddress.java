package com.example.landandproperty4d.screen.postproperty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.landandproperty4d.R;

public class LandAddress extends AppCompatActivity {
    private Toolbar toolbarLandAddress ;
    private EditText editTextCity , editTextDistric , editTextPhuong , editTextStreet , editTextHouseNumber;
    private Button buttonDoneAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_address);
        init();

        toolbarLandAddress.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandAddress.this,PostPropertyActivity.class));
            }
        });

        buttonDoneAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandAddress.this,PostPropertyActivity.class);
                Bundle goithongtin = new Bundle();
                intent.putExtra("City",editTextCity.getText().toString());
                intent.putExtra("Distric",editTextDistric.getText().toString());
                intent.putExtra("Phuong",editTextPhuong.getText().toString());
                intent.putExtra("Street",editTextStreet.getText().toString());
                intent.putExtra("HouseNumber",editTextHouseNumber.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void init(){
        toolbarLandAddress = findViewById(R.id.toolBarLandAddress);
        setSupportActionBar(toolbarLandAddress);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextCity = findViewById(R.id.editTextCity);
        editTextDistric = findViewById(R.id.editTextDistric);
        editTextPhuong = findViewById(R.id.editTextPhuong);
        editTextStreet = findViewById(R.id.editTextStreet);
        editTextHouseNumber = findViewById(R.id.editTextHouseNumber);
        buttonDoneAddress = findViewById(R.id.buttonDoneAddress);
    }
}
