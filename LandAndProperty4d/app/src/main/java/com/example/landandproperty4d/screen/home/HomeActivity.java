package com.example.landandproperty4d.screen.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.screen.postproperty.PostPropertyActivity;
import com.example.landandproperty4d.screen.viewinformationproperty.PostAdapter;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
import com.example.landandproperty4d.screen.viewmap4d.ViewMap4D;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button buttoLogout ,buttonPostProperty,buttonViewMap,buttonViewProperty,buttonNews,
            buttonYourNews,buttonManageAccount,buttonPostInformation,buttonManagePost ,buttonCkeckPost;
    private ImageView imageViewHome;
    private Toolbar toolbarHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        //test();
//        getInformationUser();

        Intent intent = getIntent();
        if( intent.getExtras() !=null && intent.getStringExtra("ruler").equals("1")){
            buttonCkeckPost.setVisibility(View.INVISIBLE);
            buttonPostInformation.setVisibility(View.INVISIBLE);
        } else if (intent.getExtras()!=null && intent.getStringExtra("ruler").equals("2")){
            buttonCkeckPost.setVisibility(View.INVISIBLE);
            buttonPostInformation.setVisibility(View.INVISIBLE);
            buttonManagePost.setFocusable(false);
            buttonManagePost.setBackground(ContextCompat.getDrawable(this,R.drawable.custome_button));
            buttonPostProperty.setFocusable(false);
            buttonPostProperty.setBackground(ContextCompat.getDrawable(this,R.drawable.custome_button));
        }

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
        buttonNews = findViewById(R.id.buttonNews);
        buttonYourNews = findViewById(R.id.buttonYourNews);
        buttonManageAccount = findViewById(R.id.buttonManageAccount);
        buttonPostInformation = findViewById(R.id.buttonPostInformation);
        buttonManagePost = findViewById(R.id.buttonManagePost);
        buttonCkeckPost = findViewById(R.id.buttonCkeckPost);
        imageViewHome = findViewById(R.id.iconHome);
        toolbarHomePage = findViewById(R.id.toolBarHomePage);
        setSupportActionBar(toolbarHomePage);
    }

}
