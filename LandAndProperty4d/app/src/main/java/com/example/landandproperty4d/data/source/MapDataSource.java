package com.example.landandproperty4d.data.source;

import com.example.landandproperty4d.data.model.User;

import rx.Observable;

public interface MapDataSource {
    void saveRegiter (String userName, String password, String name, String birthDay,
                      String address, String homeTown, String email, String phoneNumber, String placesInterest,String ruler);
    void saveDataPost(String address, String area, String contact, String detail, String homeDirection,String image, String postPlace,
            String price, String title, String typeProperty, String id);
//    User getInformationUser();
}
