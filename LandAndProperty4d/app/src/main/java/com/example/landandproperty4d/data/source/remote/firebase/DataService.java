package com.example.landandproperty4d.data.source.remote.firebase;

import com.example.landandproperty4d.data.model.Buyer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataService {
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void wiriteBuyer(String userName, String password, String name, String birthDay,
                            String address, String homeTown, String email, String phoneNumber, String placesInterest) {
        Buyer buyer = new Buyer(userName, password, name, birthDay, address, homeTown, email, phoneNumber, placesInterest);
        mDatabase.child("buyer").child(user.getUid()).setValue(buyer);
    }
}
