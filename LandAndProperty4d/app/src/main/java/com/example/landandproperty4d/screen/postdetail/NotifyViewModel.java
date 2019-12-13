package com.example.landandproperty4d.screen.postdetail;

import com.example.landandproperty4d.data.source.MapReponsitory;

import androidx.lifecycle.ViewModel;

public class NotifyViewModel extends ViewModel {
    private MapReponsitory mapReponsitory;

    public NotifyViewModel(MapReponsitory mapReponsitory) {
        this.mapReponsitory = mapReponsitory;
    }
    public void saveNotify (String namePost , String email, String interestPeople, String notifyDay , String phone , String idSeller){
        mapReponsitory.saveNotify(namePost,email,interestPeople,notifyDay,phone,idSeller);
    }
}
