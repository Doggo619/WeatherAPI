package com.base.weatherapi.retrofit;

import com.base.weatherapi.response.GeonamesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeonamesApiService {
    @GET("searchJSON")
    Call<GeonamesResponse> searchCities(
            @Query("name_startsWith") String partialCityName,
            @Query("username") String username
    );
}
