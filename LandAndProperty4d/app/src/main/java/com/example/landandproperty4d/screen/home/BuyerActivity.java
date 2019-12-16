package com.example.landandproperty4d.screen.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.Notification;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.screen.manageaccount.ManageAccountActivity;
import com.example.landandproperty4d.screen.notification.NotifyActivity;
import com.example.landandproperty4d.screen.viewinformationproperty.ViewInformationProperty;
import com.example.landandproperty4d.screen.viewmap4d.ViewMap4D;
import com.example.landandproperty4d.screen.viewnews.ViewNewActivity;
import com.example.landandproperty4d.screen.yournews.YourNewsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class BuyerActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Button buttoLogoutUser,buttonViewMapUser,buttonViewPropertyUser,buttonNewsUser,
            buttonYourNewsUser,buttonManageAccountUser;
    private ImageButton bell;
    private int dem = 0;
    private Timer timer;
    private Handler handler;
    private int i=0;
    private TextView textViewNotifyUser;
    private Toolbar toolbarHomePageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        init();
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
        buttoLogoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        buttonViewMapUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, ViewMap4D.class);
                startActivity(intent);
            }
        });

        buttonViewPropertyUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, ViewInformationProperty.class);
                startActivity(intent);
            }
        });
        buttonNewsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, ViewNewActivity.class);
                startActivity(intent);
            }
        });
        buttonYourNewsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, YourNewsActivity.class);
                startActivity(intent);
            }
        });
        buttonManageAccountUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, ManageAccountActivity.class);
                startActivity(intent);
            }
        });
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerActivity.this, NotifyActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        textViewNotifyUser = findViewById(R.id.textNotifyUser);
        bell = findViewById(R.id.imageButtonNotificationUser);
        buttonViewMapUser = findViewById(R.id.buttonViewMapUser);
        buttoLogoutUser = findViewById(R.id.buttonLogoutUser);
        buttonViewPropertyUser = findViewById(R.id.buttonViewPropertyUser);
        buttonNewsUser = findViewById(R.id.buttonNewsUser);
        buttonYourNewsUser = findViewById(R.id.buttonYourNewsUser);
        buttonManageAccountUser = findViewById(R.id.buttonManageAccountUser);
        toolbarHomePageUser = findViewById(R.id.toolBarHomePageUser);
        setSupportActionBar(toolbarHomePageUser);
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
                textViewNotifyUser.setText(""+dem);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dem = 0;
    }

}
