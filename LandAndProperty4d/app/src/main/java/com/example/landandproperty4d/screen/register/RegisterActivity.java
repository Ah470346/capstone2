package com.example.landandproperty4d.screen.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private EditText user, pass,DateOfBirth , editTextName,editTextAddress , editTextHomeTown , editTextEmail,editTextPhone,editTextConfirmPassword;
    private Button dangky;
    private FirebaseAuth mAuth;
    private TextView txtthongtin,txttaikhoan;
    private Spinner spinnerPlacesInterest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().toString() == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Họ Và Tên",Toast.LENGTH_LONG).show();
                } if(editTextAddress.getText().toString() == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Địa Chỉ",Toast.LENGTH_LONG).show();
                } if(editTextHomeTown == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Quê Quán",Toast.LENGTH_LONG).show();
                } if(DateOfBirth == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Ngày Sinh",Toast.LENGTH_LONG).show();
                } if(editTextEmail == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Email",Toast.LENGTH_LONG).show();
                } if(editTextPhone == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Số Điện Thoại",Toast.LENGTH_LONG).show();
                } if(editTextConfirmPassword == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Mật Khẩu Xác Nhận",Toast.LENGTH_LONG).show();
                } if(user == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Tên Tài Khoản",Toast.LENGTH_LONG).show();
                } if(pass == null){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Mật Khẩu",Toast.LENGTH_LONG).show();
                } else {
                    Register(user.getText().toString(), pass.getText().toString());
                }
            }
        });

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseDate();
            }
        });
    }
    private void init(){
        ArrayList<String> listPlacces = new ArrayList<String>();
        listPlacces.add("Đà Nẵng");
        listPlacces.add("Quảng Nam");
        listPlacces.add("Hà Nội");
        listPlacces.add("Hồ Chí Minh");

        user = findViewById(R.id.editTextUserName);
        pass = findViewById(R.id.editTextPassword);
        txtthongtin = findViewById(R.id.txtthongtin);
        txttaikhoan = findViewById(R.id.txttaikhoan);
        DateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextName = findViewById(R.id.editTextUserName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextHomeTown = findViewById(R.id.editTextHomeTown);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        mAuth = FirebaseAuth.getInstance();
        dangky = findViewById(R.id.dangky);
        spinnerPlacesInterest = findViewById(R.id.spinnerPlacesInterest);

        txtthongtin.setPaintFlags(txtthongtin.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txttaikhoan.setPaintFlags(txttaikhoan.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        ArrayAdapter<String> adapterPlacesInterest = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listPlacces);
        adapterPlacesInterest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlacesInterest.setAdapter(adapterPlacesInterest);
    }

    private void Register (String userName , String password){
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(RegisterActivity.this,"Tạo Thành Công",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this,"Tạo Thất Bại",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void ChooseDate (){
        final Calendar calendar = Calendar.getInstance();
        int Currentday = calendar.get(calendar.DATE);
        int Currentmonth = calendar.get(calendar.MONTH);
        int Currentyear = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
                calendar.set(year,month,dayOfMonth);
                Calendar now = Calendar.getInstance();

                if(calendar.getTime().after(now.getTime())){
                    Toast.makeText(RegisterActivity.this,"Ngày Sinh Không Hợp Lệ",Toast.LENGTH_LONG).show();
                }else {
                    DateOfBirth.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },1996,Currentmonth,Currentday);
        datePickerDialog.show();
    }

}

