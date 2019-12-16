package com.example.landandproperty4d.screen.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.landandproperty4d.R;
import com.example.landandproperty4d.data.source.MapReponsitory;
import com.example.landandproperty4d.data.source.remote.MapRemoteDataSource;
import com.example.landandproperty4d.screen.login.MainActivity;
import com.example.landandproperty4d.utils.CommonUtils;
import com.example.landandproperty4d.utils.MyViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.roger.catloadinglibrary.CatLoadingView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    private EditText user, pass,DateOfBirth , editTextName,editTextAddress , editTextHomeTown , editTextEmail,editTextPhone,editTextConfirmPassword;
    private Button dangky;
    private FirebaseAuth mAuth;
    private TextView txtthongtin,txttaikhoan;
    private Spinner spinnerPlacesInterest;
    private CatLoadingView progressBarCat;
    private RegisterViewModel mViewModel;
    private String placesInterest;
    private Toolbar toolbar;
    private RadioButton radioButtonSeller , radioButtonBuyer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        mapPing();

        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarCat.show(getSupportFragmentManager(),"");
//                Toast.makeText(RegisterActivity.this,"" +editTextName.getText().toString(),Toast.LENGTH_LONG).show();
                if(ktconnect()==false){Toast.makeText(getApplicationContext(), "vui lòng kết nối mạng Internet", Toast.LENGTH_SHORT).show();}
                 else  if(editTextName.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Họ Tên",Toast.LENGTH_LONG).show();
                }else if(editTextAddress.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Địa Chỉ",Toast.LENGTH_LONG).show();
                }else if(editTextHomeTown.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Quê Quán",Toast.LENGTH_LONG).show();
                }else if(DateOfBirth.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Ngày Sinh",Toast.LENGTH_LONG).show();
                }else if(editTextEmail.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Email",Toast.LENGTH_LONG).show();
                }else if(editTextPhone.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Số Điện Thoại",Toast.LENGTH_LONG).show();
                }else if(user.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Email Đăng Nhập",Toast.LENGTH_LONG).show();
                }else if(pass.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Mật Khẩu",Toast.LENGTH_LONG).show();
                }else if(editTextConfirmPassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,"Bạn Chưa Nhập Mật Khẩu Xác Nhận",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isSpecialEmail(editTextEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Email Không Được Chứa Kí Tự Đặc Biệt",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isErrorEmail(editTextEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Bạn Nhập Sai Định Dạng Email",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isVietnames(editTextEmail.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Bạn Nhập Email Không Hợp Lệ",Toast.LENGTH_LONG).show();
                }else if(CommonUtils.isSpecialEmail(user.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Email Đăng Nhập Không Được Chứa Kí Tự Đặc Biệt",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isErrorEmail(user.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Bạn Nhập Sai Định Dạng Email Đăng Nhập",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isVietnames(user.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Email Đăng Nhập Không Hợp Lệ",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isSpecialCharacter(pass.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Mật Khẩu Không Được Chứa Kí Tự Đặc Biệt",Toast.LENGTH_LONG).show();
                } else if(CommonUtils.isVietnames(pass.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Mật Khẩu Không Hợp Lệ",Toast.LENGTH_LONG).show();
                } else if(pass.getText().toString().length()<6){
                    Toast.makeText(RegisterActivity.this,"Mật Khẩu Phải Có Độ Dài Trên 6 kí Tự",Toast.LENGTH_LONG).show();
                } else if(!pass.getText().toString().equals(editTextConfirmPassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"Mật Khẩu Khác Mật Khẩu Xác Nhận",Toast.LENGTH_LONG).show();
                } else {
                    Register(user.getText().toString(), pass.getText().toString(),editTextName.getText().toString(),DateOfBirth.getText().toString()
                            ,editTextAddress.getText().toString(),editTextHomeTown.getText().toString(),editTextEmail.getText().toString(),editTextPhone.getText().toString(),placesInterest);
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
    private boolean ktconnect (){
        ConnectivityManager connectivityManager = (ConnectivityManager) RegisterActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    private void initView(){
        ArrayList<String> listPlacces = new ArrayList<String>();
        listPlacces.add("Đà Nẵng");
        listPlacces.add("Quảng Nam");
        listPlacces.add("Hà Nội");
        listPlacces.add("Hồ Chí Minh");
        progressBarCat = new CatLoadingView();
        user = findViewById(R.id.editTextUserName);
        pass = findViewById(R.id.editTextPassword);
        txtthongtin = findViewById(R.id.txtthongtin);
        txttaikhoan = findViewById(R.id.txttaikhoan);
        DateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextHomeTown = findViewById(R.id.editTextHomeTown);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        radioButtonBuyer = findViewById(R.id.radioButtonBuyer);
        radioButtonSeller = findViewById(R.id.radioButtonSeller);
        toolbar = findViewById(R.id.toolBarRegister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        mAuth = FirebaseAuth.getInstance();
        dangky = findViewById(R.id.dangky);
        spinnerPlacesInterest = findViewById(R.id.spinnerPlacesInterest);
        ArrayAdapter<String> adapterPlacesInterest = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listPlacces);
        adapterPlacesInterest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlacesInterest.setAdapter(adapterPlacesInterest);
    }

    private void initData(){
        MapReponsitory mapReponsitory = MapReponsitory.getInstance(MapRemoteDataSource.getsInstance());
        mViewModel = ViewModelProviders.of(this,new MyViewModelFactory(mapReponsitory))
                .get(RegisterViewModel.class);

    }

    private void mapPing(){
        spinnerPlacesInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placesInterest = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Register (final String userName, final String password, final String name, final String birthDay,
                           final String address, final String homeTown, final String email, final String phoneNumber, final String placesInterest){
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            mViewModel.writeBuyer(userName,password,name,birthDay,address,homeTown,email,phoneNumber,placesInterest,setCheckUser());
                            progressBarCat.dismiss();
                            Toast.makeText(RegisterActivity.this,"Tạo Thành Công",Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this,"Tài Khoản Đã Tồn Tại",Toast.LENGTH_SHORT).show();
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
    public  String setCheckUser(){
        if(radioButtonSeller.isChecked()) {
            return "1";
        } else if(radioButtonBuyer.isChecked()){
            return "2";
        } else return "0";
    }
}

