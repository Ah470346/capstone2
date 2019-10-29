package com.example.landandproperty4d.data.source.remote;

import com.example.landandproperty4d.data.source.MapDataSource;
import com.example.landandproperty4d.data.source.remote.firebase.DataService;

public class MapRemoteDataSource implements MapDataSource {
    private static MapRemoteDataSource sInstance;
    private DataService mDataService;

    private MapRemoteDataSource() {
    }

    public static MapRemoteDataSource  getsInstance(){
        if(sInstance == null){
            sInstance = new MapRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void saveRegiter(String userName, String password, String name, String birthDay,
                            String address, String homeTown, String email, String phoneNumber, String placesInterest) {
        mDataService = new DataService();
        mDataService.wiriteBuyer(userName, password, name, birthDay, address, homeTown, email, phoneNumber,
                placesInterest);
    }

    @Override
    public void saveDataPost(final String address, final String area, final String contact, final String detail,
            final String homeDirection,
            final String image, final String postPlace, final String price, final String title,
            final String typeProperty) {
        mDataService = new DataService();
        mDataService.saveDataPost(address, area, contact, detail, homeDirection, image, postPlace, price, title, typeProperty);
    }
}
