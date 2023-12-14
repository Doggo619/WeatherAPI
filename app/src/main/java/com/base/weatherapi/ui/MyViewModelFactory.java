//package com.base.weatherapi.ui;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.ViewModel;
//import androidx.lifecycle.ViewModelProvider;
//
//import com.base.weatherapi.repository.MyRepository;
//
//public class MyViewModelFactory implements ViewModelProvider.Factory {
//    private final Application application;
//    private final MyRepository repository;
//
//    public MyViewModelFactory(Application application, MyRepository repository) {
//        this.application = application;
//        this.repository = repository;
//    }
//
//    @NonNull
//    @Override
//    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
//        if (modelClass.isAssignableFrom(MyViewModel.class)) {
//            return (T) new MyViewModel(application, repository);
//        }
//        throw new IllegalArgumentException("Unknown ViewModel Class");
//    }
//}
