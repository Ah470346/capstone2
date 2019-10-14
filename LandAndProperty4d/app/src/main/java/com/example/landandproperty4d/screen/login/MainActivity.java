package com.example.landandproperty4d.screen.login;

import android.content.Intent;
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
import com.example.landandproperty4d.screen.home.HomeActivity;
import com.example.landandproperty4d.screen.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextUserName , editTextPassword;
    private Button buttonLogin, buttonRegister ;
    private FirebaseAuth mAuth;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin:
                login(editTextUserName.getText().toString(),editTextPassword.getText().toString());
                break;
            case R.id.buttonRegister:
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initView (){
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
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Tài Khoản hoặc mật khẩu không chính xác",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

