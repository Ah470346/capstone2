package com.example.landandproperty4d.screen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.Notification;
import com.example.landandproperty4d.screen.checkpost.CheckPostActivity;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.screen.manageaccount.ManageAccountActivity;
import com.example.landandproperty4d.screen.managerpost.ManagerPostActivityA;
import com.example.landandproperty4d.screen.managerpost.ManagerPostActivityU;
import com.example.landandproperty4d.screen.notification.NotifyActivity;
import com.example.landandproperty4d.screen.postnews.PostNewActivity;
import com.example.landandproperty4d.screen.postproperty.PostPropertyActivity;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
import com.example.landandproperty4d.screen.viewmap4d.ViewMap4D;
import com.example.landandproperty4d.screen.viewnews.ViewNewActivity;
import com.example.landandproperty4d.screen.yournews.YourNewsActivity;
import com.example.landandproperty4d.utils.CommonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button buttoLogout ,buttonPostProperty,buttonViewMap,buttonViewProperty,buttonNews,
            buttonYourNews,buttonManageAccount,buttonPostNew,buttonManagePost ,buttonCkeckPost;
    private ImageButton bell;
    private int dem = 0;
    private Timer timer;
    private Handler handler;
    private int i=0;
    private TextView textViewNotify;
    private ImageView imageViewHome;
    private Toolbar toolbarHomePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        Log.d("rolerr",CommonUtils.rule);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what < 5){
                    if(i <3000 ){
                        i++;
                        getNotify();
                    }

                }

            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        },1000,1000);
        //test();
//        getInformationUser();

        Intent intent = getIntent();
        if( intent.getExtras() !=null && intent.getStringExtra("ruler").equals("1")){
            buttonCkeckPost.setVisibility(View.INVISIBLE);
            buttonPostNew.setVisibility(View.INVISIBLE);
        } else if (intent.getExtras()!=null && intent.getStringExtra("ruler").equals("2")){
            buttonCkeckPost.setVisibility(View.INVISIBLE);
            buttonPostNew.setVisibility(View.INVISIBLE);
            buttonManagePost.setBackground(ContextCompat.getDrawable(this,R.drawable.custome_roler));
            buttonPostProperty.setBackground(ContextCompat.getDrawable(this,R.drawable.custome_roler));
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
                if(CommonUtils.rule.equals("1") || CommonUtils.rule.equals("3")) {
                    Intent intent = new Intent(HomeActivity.this, PostPropertyActivity.class);
                    startActivity(intent);
                }
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
        buttonPostNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PostNewActivity.class);
                startActivity(intent);
            }
        });
        buttonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ViewNewActivity.class);
                startActivity(intent);
            }
        });
        buttonYourNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, YourNewsActivity.class);
                startActivity(intent);
            }
        });
        buttonCkeckPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CheckPostActivity.class);
                startActivity(intent);
            }
        });
        buttonManagePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CommonUtils.rule.equals("1")){
                    Intent intent = new Intent(HomeActivity.this, ManagerPostActivityU.class);
                    startActivity(intent);
                } else if(CommonUtils.rule.equals("3")){
                    Intent intent = new Intent(HomeActivity.this, ManagerPostActivityA.class);
                    startActivity(intent);
                }
            }
        });
        buttonManageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NotifyActivity.class);
                startActivity(intent);
            }
        });

    }

    public void init(){
        textViewNotify = findViewById(R.id.textNotify);
        bell = findViewById(R.id.imageButtonNotification);
        buttonViewMap = findViewById(R.id.buttonViewMap);
        buttoLogout = findViewById(R.id.buttonLogout);
        buttonPostProperty = findViewById(R.id.buttonPostProperty);
        buttonViewProperty = findViewById(R.id.buttonViewProperty);
        buttonNews = findViewById(R.id.buttonNews);
        buttonYourNews = findViewById(R.id.buttonYourNews);
        buttonManageAccount = findViewById(R.id.buttonManageAccount);
        buttonPostNew = findViewById(R.id.buttonPostNew);
        buttonManagePost = findViewById(R.id.buttonManagePost);
        buttonCkeckPost = findViewById(R.id.buttonCkeckPost);
        imageViewHome = findViewById(R.id.iconHome);
        toolbarHomePage = findViewById(R.id.toolBarHomePage);
        setSupportActionBar(toolbarHomePage);
    }
    private void getNotify (){
        mDatabase.child("Notification").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
                for (DataSnapshot postSnapshot: nodeChild) {
                    for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                        Notification notification = snapshot.getValue(Notification.class);
                        if (notification.getIdSeller().equals(user.getUid())) {
                            dem++;
                        }
                    }
                }
                textViewNotify.setText(""+dem);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dem = 0;
    }

}
