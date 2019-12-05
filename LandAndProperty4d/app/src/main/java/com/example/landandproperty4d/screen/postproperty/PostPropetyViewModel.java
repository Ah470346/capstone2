package com.example.landandproperty4d.screen.postproperty;

import androidx.lifecycle.ViewModel;
import com.example.landandproperty4d.data.source.MapReponsitory;

public class PostPropetyViewModel extends ViewModel {
    private MapReponsitory mapReponsitory;

    public PostPropetyViewModel(MapReponsitory mapReponsitory){
        this.mapReponsitory = mapReponsitory;
    }

    public void saveDataPost(String address, String area, String contact, String detail, String homeDirection,String image, String postPlace,
            String price, String title, String typeProperty,String id,String lng,String lat,String polygonid){
        mapReponsitory.saveDataPost(address, area, contact, detail, homeDirection, image, postPlace, price, title, typeProperty,id,lng,lat,polygonid);
    }
}
