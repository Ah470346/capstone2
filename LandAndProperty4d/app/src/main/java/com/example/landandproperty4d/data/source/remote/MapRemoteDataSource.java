package com.example.landandproperty4d.data.source.remote;

import com.example.landandproperty4d.data.model.User;
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
                            String address, String homeTown, String email, String phoneNumber, String placesInterest , String ruler) {
        mDataService = new DataService();
        mDataService.wiriteUser(userName, password, name, birthDay, address, homeTown, email, phoneNumber,
                placesInterest,ruler);
    }

    @Override
    public void saveDataPost(final String address, final String area, final String contact, final String detail,
            final String homeDirection,
            final String image, final String postPlace, final String price, final String title,
            final String typeProperty,final String id,final String lng,final String lat,String polygonid,String check,String postDay) {
        mDataService = new DataService();
        mDataService.saveDataPost(address, area, contact, detail, homeDirection, image, postPlace, price, title, typeProperty,id,lng ,lat,polygonid,check,postDay);
    }

    @Override
    public void saveDataNew(String title, String content, String image,String id,String idAdmin, String postNewDay) {
        mDataService = new DataService();
        mDataService.saveDataNew(title,content,image,id,idAdmin,postNewDay);
    }

    @Override
    public void saveNotify(String namePost, String email, String interestPeople, String notifyDay, String phone, String idSeller,String id) {
        mDataService = new DataService();
        mDataService.saveDataNotify(namePost,email,interestPeople,notifyDay,phone,idSeller,id);
    }

//    @Override
//    public User getInformationUser() {
//        mDataService = new DataService();
//
//    }
}
