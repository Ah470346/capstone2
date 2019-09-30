package com.example.landandproperty4d.data.source;

import rx.Observable;

public interface MapDataSource {
    void saveRegiter (String userName, String password, String name, String birthDay,
                      String address, String homeTown, String email, String phoneNumber, String placesInterest);

}
