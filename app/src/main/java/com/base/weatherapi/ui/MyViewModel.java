//package com.base.weatherapi.ui;
//
//import android.app.Application;
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.base.weatherapi.errorHandling.ApiStatusResponse;
//import com.base.weatherapi.errorHandling.Resource;
//import com.base.weatherapi.repository.MyRepository;
//import com.base.weatherapi.response.WeatherResponse;
//
//import java.util.List;
//
//public class MyViewModel extends AndroidViewModel {
//    private MyRepository repository;
//    private MutableLiveData<WeatherResponse> weatherLiveData = new MutableLiveData<>();
//    private LiveData<List<String>> cityNamesLiveData;
//    Context context;
//    public MyViewModel(@NonNull Application application, MyRepository repository) {
//        super(application);
//        this.repository = repository;
//    }
//
//    public LiveData<Resource<ApiStatusResponse>> getWeatherApi(String cityName, String apiKey) {
//        return repository.getWeatherApi(cityName, apiKey);
//    }
//    public LiveData<List<String>> getCityNames() {
//        return repository.getCityNames(context);
//    }
//    public void setWeatherData(WeatherResponse weatherResponse) {
//        weatherLiveData.setValue(weatherResponse);
//    }
//
//    public LiveData<WeatherResponse> getWeatherData() {
//        return weatherLiveData;
//    }
//}
