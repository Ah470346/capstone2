package com.example.landandproperty4d.screen.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.screen.home.BuyerActivity;
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.register.RegisterActivity;
import com.example.landandproperty4d.utils.CommonUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUserName , editTextPassword;
    private Button buttonLogin, buttonRegister ;
    private FirebaseAuth mAuth;
    private CatLoadingView progressBarCat;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private boolean ktconnect (){
        ConnectivityManager connectivityManager = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLogin:
                if (ktconnect() == false) {
                    Toast.makeText(getApplicationContext(), "vui lòng kết nối mạng Internet", Toast.LENGTH_SHORT).show();
                } else if (editTextUserName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Email Đăng Nhập", Toast.LENGTH_SHORT).show();
                } else if (editTextPassword.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Bạn Chưa Nhập Mật Khẩu", Toast.LENGTH_SHORT).show();
                } else {
                    progressBarCat.show(getSupportFragmentManager(), "");
                    login(editTextUserName.getText().toString(), editTextPassword.getText().toString());
                }

                break;
            case R.id.buttonRegister:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView (){
        progressBarCat = new CatLoadingView();
        mAuth = FirebaseAuth.getInstance();
        buttonRegister = findViewById(R.id.buttonRegister);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUserName = findViewById(R.id.editTextUserName);
        buttonLogin = findViewById(R.id.buttonLogin);
        toolbar = findViewById(R.id.toolBarLogin);
        setSupportActionBar(toolbar);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);




    }

    private void login (String userName, String password){
        Log.d("AAA",editTextUserName.getText().toString());
        Log.d("AAA",editTextPassword.getText().toString());
        mAuth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    CommonUtils.rule = user.getRuler();
                                    if (user.getRuler().equals("1") || user.getRuler().equals("3")){
                                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                        intent.putExtra("ruler", user.getRuler());
                                        startActivity(intent);
                                        finish();
                                    } else if (user.getRuler().equals("2")){
                                        Intent intent = new Intent(MainActivity.this, BuyerActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            progressBarCat.dismiss();

                        } else {
                            progressBarCat.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Tài Khoản hoặc mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

