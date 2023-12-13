package com.base.weatherapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeonamesResponse {
    @SerializedName("geonames")
    private List<Geoname> geonames;

    public List<Geoname> getGeonames() {
        return geonames;
    }
}

