package com.base.weatherapi.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String BASE_URL_CITY = "http://api.geonames.org/";
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static Retrofit createProxyClient() {
        OkHttpClient proxyClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    // Intercept the request and modify it to use HTTP instead of HTTPS
                    Request originalRequest = chain.request();
                    Request newRequest = originalRequest.newBuilder()
                            .url(originalRequest.url().toString().replace("https://", "http://"))
                            .build();
                    return chain.proceed(newRequest);
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_CITY)
                .client(proxyClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

