package com.example.landandproperty4d.screen.manageaccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
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
import com.roger.catloadinglibrary.CatLoadingView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ManageAccountActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Toolbar toolbarProfile;
    private CatLoadingView progressBarCat;
    private Button buttonChangePassword;
    private EditText editTextNameProfile, editTextBirthProfile , editTextInterestProfile , editTextGmailProfile , editTextPhoneProfile;
    private TextView changeName, changeBirth , changeInterest , changeEmail , changePhone , DoneName , DoneBirth , DoneInterest , DoneEmial ,DonePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_account);
        init();
        progressBarCat.show(getSupportFragmentManager(),"");
        pullDataUser();
        toolbarProfile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void init(){
        progressBarCat = new CatLoadingView();
        toolbarProfile = findViewById(R.id.toolBarManagerAccount);
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        buttonChangePassword = findViewById(R.id.buttonChancePassword);
        editTextBirthProfile = findViewById(R.id.editTextBirthProfile);
        editTextGmailProfile = findViewById(R.id.editTextGmailProfile);
        editTextNameProfile = findViewById(R.id.editTextNameProfile);
        editTextInterestProfile = findViewById(R.id.editTextInterestProfile);
        editTextPhoneProfile = findViewById(R.id.editTextPhoneProfile);
        editTextPhoneProfile.setFocusable(false);
        editTextInterestProfile.setFocusable(false);
        editTextNameProfile.setFocusable(false);
        editTextGmailProfile.setFocusable(false);
        editTextBirthProfile.setFocusable(false);
        changeBirth = findViewById(R.id.changeBirth);
        changeEmail = findViewById(R.id.changeEmail);
        changeInterest = findViewById(R.id.changeInterest);
        changeName = findViewById(R.id.changeName);
        changePhone = findViewById(R.id.changePhone);
        DoneBirth = findViewById(R.id.DoneBirth);
        DoneEmial = findViewById(R.id.DoneEmail);
        DoneInterest = findViewById(R.id.DoneInterest);
        DoneName = findViewById(R.id.DoneName);
        DonePhone = findViewById(R.id.DonePhone);
        DonePhone.setVisibility(View.INVISIBLE);
        DoneName.setVisibility(View.INVISIBLE);
        DoneInterest.setVisibility(View.INVISIBLE);
        DoneEmial.setVisibility(View.INVISIBLE);
        DoneBirth.setVisibility(View.INVISIBLE);
        changeName.setOnClickListener(this);
        changePhone.setOnClickListener(this);
        changeInterest.setOnClickListener(this);
        changeEmail.setOnClickListener(this);
        changeBirth.setOnClickListener(this);
        DonePhone.setOnClickListener(this);
        DoneBirth.setOnClickListener(this);
        DoneName.setOnClickListener(this);
        DoneEmial.setOnClickListener(this);
        DoneInterest.setOnClickListener(this);
        buttonChangePassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changeBirth :
                changeBirth.setVisibility(View.INVISIBLE);
                DoneBirth.setVisibility(View.VISIBLE);
                ChooseDate();
                break;
            case R.id.changeEmail :
                changeEmail.setVisibility(View.INVISIBLE);
                DoneEmial.setVisibility(View.VISIBLE);
                editTextGmailProfile.setFocusableInTouchMode(true);
                editTextGmailProfile.requestFocus();
                break;
            case R.id.changeInterest :
                changeInterest.setVisibility(View.INVISIBLE);
                DoneInterest.setVisibility(View.VISIBLE);
                AccountDialog contact = new AccountDialog();
                contact.show(getSupportFragmentManager(),"no");
                break;
            case R.id.changeName :
                changeName.setVisibility(View.INVISIBLE);
                DoneName.setVisibility(View.VISIBLE);
                editTextNameProfile.setFocusableInTouchMode(true);
                editTextNameProfile.requestFocus();
                break;
            case R.id.changePhone :
                changePhone.setVisibility(View.INVISIBLE);
                DonePhone.setVisibility(View.VISIBLE);
                editTextPhoneProfile.setFocusableInTouchMode(true);
                editTextPhoneProfile.requestFocus();
                break;
            case R.id.DoneBirth :
                if(editTextBirthProfile.getText().toString().equals("")){
                    Toast.makeText(this,"Ngày Sinh Trống",Toast.LENGTH_LONG).show();
                } else {
                    DoneBirth.setVisibility(View.INVISIBLE);
                    changeBirth.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Thay Đổi Thành Công", Toast.LENGTH_LONG).show();
                    pushBirth();
                }
                break;
            case R.id.DoneEmail :
                if(editTextGmailProfile.getText().toString().equals("")){
                    Toast.makeText(this,"Email Trống",Toast.LENGTH_LONG).show();
                }else if(CommonUtils.isSpecialEmail(editTextGmailProfile.getText().toString())){
                    Toast.makeText(this,"Email Không Được Chứa Kí Tự Đặc Biệt",Toast.LENGTH_LONG).show();
                } else if (CommonUtils.isErrorEmail(editTextGmailProfile.getText().toString())){
                    Toast.makeText(this,"Bạn Nhập Sai Định Dạng Email",Toast.LENGTH_LONG).show();
                } else {
                    DoneEmial.setVisibility(View.INVISIBLE);
                    changeEmail.setVisibility(View.VISIBLE);
                    editTextGmailProfile.setFocusable(false);
                    InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm1.hideSoftInputFromWindow(editTextGmailProfile.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    pushEmail();
                    Toast.makeText(this, "Thay Đổi Thành Công", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.DoneInterest :
                if(editTextInterestProfile.getText().toString().equals("")){
                    Toast.makeText(this,"Địa Điểm Quan Tâm Trống",Toast.LENGTH_LONG).show();
                } else {
                    DoneInterest.setVisibility(View.INVISIBLE);
                    changeInterest.setVisibility(View.VISIBLE);
                    pushInterest();
                    Toast.makeText(this, "Thay Đổi Thành Công", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.DoneName :
                if(editTextNameProfile.getText().toString().equals("")){
                    Toast.makeText(this,"Họ Tên Trống",Toast.LENGTH_LONG).show();
                } else {
                    DoneName.setVisibility(View.INVISIBLE);
                    changeName.setVisibility(View.VISIBLE);
                    editTextNameProfile.setFocusable(false);
                    InputMethodManager imm3 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm3.hideSoftInputFromWindow(editTextNameProfile.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    pushName();
                    Toast.makeText(this, "Thay Đổi Thành Công", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.DonePhone :
                if(editTextPhoneProfile.getText().toString().equals("")){
                    Toast.makeText(this,"Số Điện Thoại Trống",Toast.LENGTH_LONG).show();
                } else {
                    DonePhone.setVisibility(View.INVISIBLE);
                    changePhone.setVisibility(View.VISIBLE);
                    editTextPhoneProfile.setFocusable(false);
                    InputMethodManager imm4 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm4.hideSoftInputFromWindow(editTextPhoneProfile.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    pushPhone();
                    Toast.makeText(this, "Thay Đổi Thành Công", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonChancePassword :
                Intent intent = new Intent(ManageAccountActivity.this,ChangePassActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void pullDataUser(){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1){
                    User user1 = snapshot.getValue(User.class);
                    if(user.getUid().equals(snapshot.getKey())){
                        editTextNameProfile.setText(user1.getName());
                        editTextBirthProfile.setText(user1.getBirthDay());
                        editTextGmailProfile.setText(user1.getEmail());
                        editTextInterestProfile.setText(user1.getPlacesInterest());
                        editTextPhoneProfile.setText(user1.getPhoneNumber());
                        break;
                    }
                }
                progressBarCat.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void pushName (){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1){
                    User user1 = snapshot.getValue(User.class);
                    if(user.getUid().equals(snapshot.getKey())){
                        snapshot.getRef().child("name").setValue(editTextNameProfile.getText().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushBirth (){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1){
                    User user1 = snapshot.getValue(User.class);
                    if(user.getUid().equals(snapshot.getKey())){
                        snapshot.getRef().child("birthDay").setValue(editTextBirthProfile.getText().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushInterest (){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1){
                    User user1 = snapshot.getValue(User.class);
                    if(user.getUid().equals(snapshot.getKey())){
                        snapshot.getRef().child("placesInterest").setValue(editTextInterestProfile.getText().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushEmail (){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1){
                    User user1 = snapshot.getValue(User.class);
                    if(user.getUid().equals(snapshot.getKey())){
                        snapshot.getRef().child("email").setValue(editTextGmailProfile.getText().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void pushPhone (){
        mDatabase.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> nodeChild1 = dataSnapshot.getChildren();
                for (DataSnapshot snapshot: nodeChild1){
                    User user1 = snapshot.getValue(User.class);
                    if(user.getUid().equals(snapshot.getKey())){
                        snapshot.getRef().child("phoneNumber").setValue(editTextPhoneProfile.getText().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
                    Toast.makeText(ManageAccountActivity.this,"Ngày Sinh Không Hợp Lệ",Toast.LENGTH_LONG).show();
                }else {
                    editTextBirthProfile.setText(simpleDateFormat.format(calendar.getTime()));
                }
            }
        },1996,Currentmonth,Currentday);
        datePickerDialog.show();
    }
}
