package com.example.landandproperty4d.data.source;

import com.example.landandproperty4d.data.source.remote.MapRemoteDataSource;

public class MapReponsitory {

    private static MapReponsitory sInstance;
    private MapRemoteDataSource mMapRemoteDataSource;

    private MapReponsitory(MapRemoteDataSource mapRemoteDataSource) {
        mMapRemoteDataSource = mapRemoteDataSource;
    }

    public static MapReponsitory getInstance(MapRemoteDataSource mapRemoteDataSource){
        if(sInstance == null){
            sInstance = new MapReponsitory(mapRemoteDataSource);
        }
        return sInstance ;
    }

    public void saveRegiter(String userName, String password, String name, String birthDay,
                            String address, String homeTown, String email, String phoneNumber, String placesInterest){
        mMapRemoteDataSource.saveRegiter(userName,password,name,birthDay,address,homeTown,email,phoneNumber,placesInterest);
    }
}
