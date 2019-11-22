package com.example.landandproperty4d.screen.register;

import com.example.landandproperty4d.data.source.MapReponsitory;

import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {
    private MapReponsitory mapReponsitory;

    public RegisterViewModel(MapReponsitory mapReponsitory){
        this.mapReponsitory = mapReponsitory;
    }

    public void writeBuyer(String userName, String password, String name, String birthDay,
                           String address, String homeTown, String email, String phoneNumber, String placesInterest,String ruler){
        mapReponsitory.saveRegister(userName,password,name,birthDay,address,homeTown,email,phoneNumber,placesInterest,ruler);
    }
}
