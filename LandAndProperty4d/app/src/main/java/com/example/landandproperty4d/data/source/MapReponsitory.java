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

    public void saveRegister(String userName, String password, String name, String birthDay,
                            String address, String homeTown, String email, String phoneNumber, String placesInterest,String ruler){
        mMapRemoteDataSource.saveRegiter(userName,password,name,birthDay,address,homeTown,email,phoneNumber,placesInterest,ruler);
    }

    public void saveDataPost(String address, String area, String contact, String detail, String homeDirection,String image, String postPlace,
            String price, String title, String typeProperty,String id,String lng,String lat,String polygonid ,String check,String postDay){
        mMapRemoteDataSource.saveDataPost(address, area, contact, detail, homeDirection, image, postPlace, price, title, typeProperty,id,lng,lat,polygonid,check,postDay);
    }

    public void saveDataNew(String title , String content,String image ,String id,String idAdmin, String postNewDay){
        mMapRemoteDataSource.saveDataNew(title,content,image,id,idAdmin,postNewDay);
    }

    public void saveNotify(String namePost , String email, String interestPeople,String notifyDay , String phone , String idSeller){
        mMapRemoteDataSource.saveNotify(namePost,email,interestPeople,notifyDay,phone,idSeller);
    }
}
