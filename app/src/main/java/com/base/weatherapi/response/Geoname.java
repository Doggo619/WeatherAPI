package com.base.weatherapi.response;

import com.google.gson.annotations.SerializedName;

public class Geoname {
    @SerializedName("name")
    private String cityName;

    public String getCityName() {
        return cityName;
    }
}
