package com.base.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.base.weatherapi.response.Geoname;
import com.base.weatherapi.response.GeonamesResponse;
import com.base.weatherapi.response.WeatherResponse;
import com.base.weatherapi.retrofit.ApiService;
import com.base.weatherapi.retrofit.GeonamesApiService;
import com.base.weatherapi.retrofit.GeonamesRetrofitClient;
import com.base.weatherapi.retrofit.RetrofitClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    MaterialCardView weatherCard;
    View errorCard;
    MaterialTextView tvCityName, tvWeatherCondition, tvTemperature, tvHumidity, tvWindSpeed, tvRain;
    MaterialButton fetchWeather;
    ImageView weatherImage;
    TextInputLayout tvCity;
    TextInputEditText etCity;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCity = findViewById(R.id.tvCity);
        etCity = findViewById(R.id.etCity);
        weatherCard = findViewById(R.id.weatherCard);
        errorCard = findViewById(R.id.errorCards);
        tvCityName = findViewById(R.id.tvCityName);
        tvWeatherCondition = findViewById(R.id.tvWeatherCondition);
        weatherImage = findViewById(R.id.ivWeatherCondition);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvHumidity = findViewById(R.id.tvHumdity);
        tvWindSpeed = findViewById(R.id.tvWindSpeed);
        tvRain = findViewById(R.id.tvRain);

        fetchWeather = findViewById(R.id.btnGetAPI);


        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 1) {
                    fetchCityNames(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fetchWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.reload_sound);
                mediaPlayer.start();
                if (validateCityName(etCity.getText().toString().trim())) {
                    weatherCard.setVisibility(View.VISIBLE);
                    errorCard.setVisibility(View.GONE);
                    String apiKey = "5a869ec58ba3be08ff9d8d0711ccc4e3";
                    String cityName = etCity.getText().toString().trim();

                    ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

                    Call<WeatherResponse> call = apiService.getWeather(cityName, apiKey);

                    call.enqueue(new Callback<WeatherResponse>() {
                        @Override
                        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                WeatherResponse weatherResponse = response.body();

                                tvCityName.setText("Weather of " + cityName);
                                String weatherCondition = "N/A";

                                if (weatherResponse.getWeatherList() != null && !weatherResponse.getWeatherList().isEmpty()) {
                                    weatherImage.setVisibility(View.VISIBLE);
                                    WeatherResponse.Weather weather = weatherResponse.getWeatherList().get(0);
                                    weatherCondition = weather.getMainCondition();
                                    switch (weatherCondition) {
                                        case "Clear":
                                            weatherImage.setImageResource(R.drawable.clear);
                                            break;
                                        case "Clouds":
                                            weatherImage.setImageResource(R.drawable.clouds);
                                            break;
                                        case "Rain":
                                            weatherImage.setImageResource(R.drawable.rain);
                                            break;
                                        case "Drizzle":
                                            weatherImage.setImageResource(R.drawable.drizzle);
                                            break;
                                        case "Thunderstorm":
                                            weatherImage.setImageResource(R.drawable.thunderstorm);
                                            break;
                                        case "Snow":
                                            weatherImage.setImageResource(R.drawable.snow);
                                            break;
                                        case "Mist":
                                            weatherImage.setImageResource(R.drawable.mist);
                                            break;
                                        case "Fog":
                                            weatherImage.setImageResource(R.drawable.fog);
                                            break;
                                        case "Smoke":
                                            weatherImage.setImageResource(R.drawable.smoke);
                                            break;
                                        case "Haze":
                                            weatherImage.setImageResource(R.drawable.haze);
                                            break;
                                        case "Dust":
                                            weatherImage.setImageResource(R.drawable.dust);
                                            break;
                                        case "Sand":
                                            weatherImage.setImageResource(R.drawable.sand);
                                            break;
                                        case "Ash":
                                            weatherImage.setImageResource(R.drawable.ash);
                                            break;
                                        case "Squall":
                                            weatherImage.setImageResource(R.drawable.squall);
                                            break;
                                        case "Tornado":
                                            weatherImage.setImageResource(R.drawable.tornado);
                                            break;
                                        default:
                                            weatherImage.setImageResource(R.drawable.clear);
                                            break;
                                    }
                                    tvWeatherCondition.setText("Weather Condition: " + weatherCondition);
                                }


                                double temperatureKelvin = weatherResponse.getMain().getTemp();
                                double temperatureCelsius = temperatureKelvin - 273.15;
                                String temperature = String.format("%.2f", temperatureCelsius);
                                tvTemperature.setText("Temperature: " + temperature + "°C");

                                int humidity = weatherResponse.getMain().getHumidity();
                                tvHumidity.setText("Humdity: " + humidity + "%");

                                int windSpeed = (int) (weatherResponse.getWind().getSpeed() * 3.6);
                                tvWindSpeed.setText("Wind Speed: " + windSpeed + " km/hr");

                                String rainChances;
                                if (weatherResponse.getRain() != null && weatherResponse.getRain().getOneHour() != 0) {
                                    // Rain in the last 1 hour (if available)
                                    double rain1h = weatherResponse.getRain().getOneHour();
                                    rainChances = String.format("%.2f", rain1h) + "%";
                                    tvRain.setText("Rain Chances: " + rainChances);
                                } else {
                                    rainChances = "0%";
                                    tvRain.setText("Rain Chances: " + rainChances);
                                }
                            } else {
                                weatherCard.setVisibility(View.GONE);
                                errorCard.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherResponse> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Network Request Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void fetchCityNames(String partialCityName) {
        Log.d("FetchCityNames", "Fetching cities for: " + partialCityName);
        String geonamesUsername = "Doggo";
        GeonamesApiService geonamesApiService = GeonamesRetrofitClient.createService();

        Call<GeonamesResponse> call = geonamesApiService.searchCities(partialCityName, geonamesUsername);
        Log.d("FetchCityNames", "Request URL: " + call.request().url());
        Log.d("FetchCityNames", "Request Headers: " + call.request().headers());

        call.enqueue(new Callback<GeonamesResponse>() {
            @Override
            public void onResponse(Call<GeonamesResponse> call, Response<GeonamesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Geoname> geonames = response.body().getGeonames();
                    List<String> cityNames = new ArrayList<>();

                    for (Geoname geoname : geonames) {
                        cityNames.add(geoname.getCityName());
                    }
                    Log.d("Adapter", "City Names: " + cityNames.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etCity.setVisibility(View.VISIBLE);
                            adapter.clear();
                            adapter.addAll(cityNames);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    Log.e("FetchCityNames","Failed to fetch city names. Response code:"  + response.code());
                }
            }

            @Override
            public void onFailure(Call<GeonamesResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fetching City Failure" + t, Toast.LENGTH_SHORT).show();
                Log.d("Main Activity", "Fetching City Failure", t);
            }
        });
    }

    private boolean validateCityName(String cityName) {
        if (cityName != null && !cityName.isEmpty()) {
            return true;
        } else {
            tvCity.setError("Please Enter City Name");
            return false;
        }
    }
}