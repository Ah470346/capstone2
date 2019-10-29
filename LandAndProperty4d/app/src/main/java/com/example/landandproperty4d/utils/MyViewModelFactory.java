package com.example.landandproperty4d.utils;

import com.example.landandproperty4d.data.source.MapReponsitory;
import com.example.landandproperty4d.screen.postproperty.PostPropetyViewModel;
import com.example.landandproperty4d.screen.register.RegisterViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private MapReponsitory mapReponsitory;
    public MyViewModelFactory(MapReponsitory mapReponsitory){
        this.mapReponsitory = mapReponsitory;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == RegisterViewModel.class){
            return (T) new RegisterViewModel(mapReponsitory);
        }
        if (modelClass == PostPropetyViewModel.class){
            return (T) new PostPropetyViewModel(mapReponsitory);
        } else{
            return super.create(modelClass);
        }
    }
}
