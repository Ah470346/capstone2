package com.example.landandproperty4d.data.source.remote.firebase;

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

    public void saveDataPost(String address, String area, String contact, String detail, String homeDirection, String image, String postPlace, String price, String title, String typeProperty,String id) {
        PostProperty postProperty = new PostProperty(image, title, typeProperty, area, postPlace, address, homeDirection, price, contact, detail, id , CommonUtils.getSimpleDateFormatPost());
        mDatabase.child("post").child(user.getUid()).push().setValue(postProperty);
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
