package com.example.landandproperty4d.screen.postnews;

import com.example.landandproperty4d.data.source.MapReponsitory;

import androidx.lifecycle.ViewModel;

public class NewModelView extends ViewModel {
    private MapReponsitory mapReponsitory;

    public NewModelView(MapReponsitory mapReponsitory) {
        this.mapReponsitory = mapReponsitory;
    }
    public void saveDataNew (String title , String content , String image ,String id,String idAdmin, String postNewDay ){
        mapReponsitory.saveDataNew(title,content,image,id,idAdmin,postNewDay);
    }
}
