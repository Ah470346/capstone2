package com.example.landandproperty4d.screen.register;

import android.view.View;

import com.example.landandproperty4d.data.source.MapReponsitory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    private MapReponsitory mapReponsitory;

    public RegisterViewModel(MapReponsitory mapReponsitory){
        this.mapReponsitory = mapReponsitory;
    }

    public void writeBuyer(String userName, String password, String name, String birthDay,
                           String address, String homeTown, String email, String phoneNumber, String placesInterest){
        mapReponsitory.saveRegiter(userName,password,name,birthDay,address,homeTown,email,phoneNumber,placesInterest);
    }
}
