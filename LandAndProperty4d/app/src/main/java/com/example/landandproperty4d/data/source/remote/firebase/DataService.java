package com.example.landandproperty4d.data.source.remote.firebase;

import com.example.landandproperty4d.data.model.New;
import com.example.landandproperty4d.data.model.Notification;
import com.example.landandproperty4d.data.model.User;
import com.example.landandproperty4d.data.model.PostProperty;
import com.example.landandproperty4d.utils.CommonUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

public class DataService {

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void wiriteUser(String userName, String password, String name, String birthDay, String address, String homeTown, String email, String phoneNumber, String placesInterest, String ruler) {
        User buyer = new User(userName, password, name, birthDay, address, homeTown, email, phoneNumber, placesInterest,ruler);
        mDatabase.child("user").child(user.getUid()).setValue(buyer);
    }

    public void saveDataPost(String address, String area, String contact, String detail, String homeDirection, String image, String postPlace, String price, String title, String typeProperty,String id,String lng,String lat,String polygonid,String check,String postDay) {
        PostProperty postProperty = new PostProperty(image, title, typeProperty, area, postPlace, address, homeDirection, price, contact, detail, id ,lng,lat,polygonid,check, postDay);
        mDatabase.child("post").child(user.getUid()).push().setValue(postProperty);
    }

    public void saveDataNew (String title , String content , String image ,String id,String idAdmin,String postNewDay){
        New news = new New(title,content,image,id,idAdmin,postNewDay) ;
        mDatabase.child("news").child(user.getUid()).push().setValue(news);
    }

    public void saveDataNotify (String namePost , String email , String interestPeople ,String notifyDay,String phone,String idSeller){
        Notification notification = new Notification(namePost,email,interestPeople,notifyDay,phone,idSeller) ;
        mDatabase.child("Notification").child(user.getUid()).setValue(notification);
    }
//    public User getInformationUser(){
//        mDatabase.child("user").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User human = new User();
//                //Iterable<DataSnapshot> nodeChild = dataSnapshot.getChildren();
//                human = dataSnapshot.getValue(User.class);
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

}
