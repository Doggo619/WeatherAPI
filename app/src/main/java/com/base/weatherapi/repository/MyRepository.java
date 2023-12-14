//package com.base.weatherapi.repository;
//
//import android.app.Application;
//import android.content.Context;
//import android.content.res.Resources;
//import android.util.Log;
//
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.base.weatherapi.R;
//import com.base.weatherapi.errorHandling.ApiStatusResponse;
//import com.base.weatherapi.errorHandling.Resource;
//import com.base.weatherapi.response.WeatherResponse;
//import com.base.weatherapi.retrofit.ApiService;
//import com.base.weatherapi.retrofit.RetrofitClient;
//import com.base.weatherapi.utils.Constants;
//import com.google.gson.Gson;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.SocketTimeoutException;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class MyRepository {
//    private Application application;
//    private ApiService apiService;
//    private RetrofitClient retrofitClient;
//    Context context;
//
//    public MyRepository(Application application, Context context) {
//        this.application = application;
//        this.context = application.getApplicationContext();
//        apiService = RetrofitClient.getClient().create(ApiService.class);
//    }
//
//    public LiveData<List<String>> getCityNames(Context context) {
//        MutableLiveData<List<String>> cityNamesLiveData = new MutableLiveData<>();
//        loadCityNamesFromJson(context, cityNamesLiveData);
//        return cityNamesLiveData;
//    }
//
//    public LiveData<Resource<ApiStatusResponse>> getWeatherApi(String cityName, String apiKey) {
//
//        MutableLiveData<Resource<ApiStatusResponse>> apiResponse = new MutableLiveData<>();
//
//        Call<WeatherResponse> call = apiService.getWeather(cityName, apiKey);
//
//        call.enqueue(new Callback<WeatherResponse>() {
//            @Override
//            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
//                WeatherResponse body = response.body();
//                if (response.isSuccessful() && response.body() != null) {
//                    if (body == null) {
//                        apiResponse.postValue(Resource.error("", new ApiStatusResponse(response.code(), response.message())));
//                        return;
//                    }
//                    if (body.getStatus() == Constants.STATUS_CODE_SUCCESS) {
//                        apiResponse.postValue(Resource.success(new ApiStatusResponse(body.getStatus(), body.getMessage())));
//                    } else if (body.getStatus() == Constants.STATUS_CODE_TOKEN_MISMATCH || body.getStatus() == Constants.STATUS_CODE_SERVER_RESPONSE_MISSING_DATA) {
////                    clearAllTables();
//                        apiResponse.postValue(Resource.error("", new ApiStatusResponse(body.getStatus(), "")));
//                    } else if (body.getStatus() == Constants.DATA_NOT_FOUND) {
//                        apiResponse.postValue(Resource.error("", new ApiStatusResponse(body.getStatus(), body.getMessage())));
//                    } else {
//                        apiResponse.postValue(Resource.error("", new ApiStatusResponse(body.getStatus(), "")));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<WeatherResponse> call, Throwable t) {
//                if (t instanceof SocketTimeoutException) {
//                    apiResponse.postValue(Resource.error("", new ApiStatusResponse(Constants.STATUS_CODE_TIMEOUT, context.getResources().getString(R.string.connection_timeout_msg))));
//                } else if (t instanceof IllegalStateException) {
//                    apiResponse.postValue(Resource.error("", new ApiStatusResponse(-1, context.getResources().getString(R.string.connection_illegal_state_exception_msg))));
//                } else {
//                    apiResponse.postValue(Resource.error("", new ApiStatusResponse(-1, t.getMessage())));
//                }
//            }
//        });
//        return apiResponse;
//    }
//
//    private List<String> loadCityNamesFromJson(Context context, MutableLiveData<List<String>> cityNamesLiveData) {
//        List<String> cityNames = new ArrayList<>();
//        try {
//            if (!cityNames.isEmpty()){
//                Resources resources = context.getResources();
//                InputStream is = resources.openRawResource(resources.getIdentifier("citynames", "raw", context.getPackageName()));
//                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
//
//                StringBuilder stringBuilder = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    stringBuilder.append(line);
//                }
//
//                String json = stringBuilder.toString();
//                Gson gson = new Gson();
//                cityNames = gson.fromJson(json, List.class);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        cityNamesLiveData.postValue(cityNames);
//        return cityNames;
//    }
//}
