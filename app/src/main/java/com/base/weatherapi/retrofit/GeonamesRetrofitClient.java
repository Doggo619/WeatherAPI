package com.base.weatherapi.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GeonamesRetrofitClient {
    private static final String BASE_URL = "http://api.geonames.org/";

    public static GeonamesApiService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(GeonamesApiService.class);
    }
}
