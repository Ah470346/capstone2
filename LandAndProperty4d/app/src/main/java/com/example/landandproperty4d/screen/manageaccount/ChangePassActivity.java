package com.example.landandproperty4d.screen.manageaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.utils.CommonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChangePassActivity extends AppCompatActivity {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private EditText editTextPass , editTextPassNew , editTextPassNewAgain;
    private Button buttonDongY;
    private Toolbar toolbarChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        init();
        toolbarChangePassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buttonDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextPass.getText().toString().equals("")){
                    Toast.makeText(ChangePassActivity.this,"Bạn Chưa Nhập Mật Khẩu",Toast.LENGTH_LONG).show();
                } else if (editTextPassNew.getText().toString().equals("")){
                    Toast.makeText(ChangePassActivity.this,"Bạn Chưa Nhập Mật Khẩu Mới",Toast.LENGTH_LONG).show();
                } else if (editTextPassNewAgain.getText().toString().equals("")){
                    Toast.makeText(ChangePassActivity.this,"Bạn Chưa Nhập Mập Khẩu Xác Nhận ",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isVietnames(editTextPass.getText().toString())){
                    Toast.makeText(ChangePassActivity.this,"Mật Khẩu Không Hợp Lệ ",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isSpecialCharacter(editTextPass.getText().toString()) || CommonUtils.isSpecialCharacter(editTextPassNew.getText().toString())
                || CommonUtils.isSpecialCharacter(editTextPassNewAgain.getText().toString())){
                    Toast.makeText(ChangePassActivity.this,"Mật Khẩu Không Được Chứa Kí Tự Đặc Biệt ",Toast.LENGTH_LONG).show();
                }
                else {
                    mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterable<DataSnapshot> nollChild = dataSnapshot.getChildren();
                            for(DataSnapshot snapshot : nollChild){
                                User user1 = snapshot.getValue(User.class);
                                if(user.getUid().equals(snapshot.getKey())){
                                    if(editTextPassNew.getText().toString().length()<6) {
                                        Toast.makeText(ChangePassActivity.this, "Mật Khẩu Phải Có Độ Dài Trên 6 Kí Tự", Toast.LENGTH_LONG).show();
                                    } else if(user1.getPassword().equals(editTextPass.getText().toString())
                                            && editTextPassNew.getText().toString().equals(editTextPassNewAgain.getText().toString())
                                            && !editTextPass.toString().equals(editTextPassNew.getText().toString())){
                                        snapshot.getRef().child("password").setValue(editTextPassNew.getText().toString());
                                        Toast.makeText(ChangePassActivity.this,"Đổi Mật Khẩu Thành Công",Toast.LENGTH_LONG).show();
                                    } else if(editTextPass.getText().toString().equals(editTextPassNew.getText().toString())){
                                        Toast.makeText(ChangePassActivity.this,"Mật Khẩu Mới Trùng Với Mật Khẩu Hiện Tại",Toast.LENGTH_LONG).show();
                                    } else if(!editTextPassNew.getText().toString().equals(editTextPassNewAgain.getText().toString())){
                                        Toast.makeText(ChangePassActivity.this,"Mật Khẩu Mới Khác Mật Khẩu Xác Nhận",Toast.LENGTH_LONG).show();
                                    } else Toast.makeText(ChangePassActivity.this,"Bạn Nhập Sai Mật Khẩu Hiện Tại",Toast.LENGTH_LONG).show();
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
    public void init() {
        toolbarChangePassword = findViewById(R.id.toolBarChangePassword);
        setSupportActionBar(toolbarChangePassword);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editTextPass = findViewById(R.id.editTextPass);
        editTextPassNew = findViewById(R.id.editTextPassNew);
        editTextPassNewAgain = findViewById(R.id.editTextPassNewAgain);
        buttonDongY = findViewById(R.id.buttonDongY);
    }
}
