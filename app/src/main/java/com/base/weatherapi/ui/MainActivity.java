package com.base.weatherapi.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.base.weatherapi.R;
import com.base.weatherapi.errorHandling.ApiStatusResponse;
import com.base.weatherapi.errorHandling.Resource;
import com.base.weatherapi.response.WeatherResponse;
import com.base.weatherapi.retrofit.ApiService;
import com.base.weatherapi.retrofit.RetrofitClient;
import com.base.weatherapi.utils.Constants;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    MaterialCardView weatherCard;
    View errorCard;
    MaterialTextView tvCityName, tvWeatherCondition, tvTemperature, tvHumidity, tvWindSpeed, tvRain;
    MaterialButton fetchWeather;
    ImageView weatherImage;
    TextInputLayout tvCity;
    MaterialAutoCompleteTextView etCity;
    ArrayAdapter<String> adapter;
    List<String> cityNames;
    Application application;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Handler handler = new Handler();
            ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

            databaseWriteExecutor.execute(() -> {
                cityNames = loadCityNames(this);
            });


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

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
            etCity.setAdapter(adapter);
            etCity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() > 1) {
                        filterCityNames(charSequence.toString());
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
                                    weatherImage.setVisibility(View.GONE);
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
    private void filterCityNames(String partialCityName) {
        List<String> filteredCities = getFilteredCities(partialCityName);
        adapter.clear();
        adapter.addAll(filteredCities);
        adapter.notifyDataSetChanged();
    }

    private List<String> getFilteredCities(String partialCityName) {
        List<String> filteredCities = new ArrayList<>();

        for (String cityName : cityNames) {
            if (cityName.toLowerCase().contains(partialCityName.toLowerCase())) {
                filteredCities.add(cityName);
            }
        }

        return filteredCities;
    }


    private boolean validateCityName(String cityName) {
        if (cityName != null && !cityName.isEmpty()) {
            return true;
        } else {
            etCity.setError("Please Enter City Name");
            return false;
        }
    }
    public static List<String> loadCityNamesFromJson(Context context) {
        List<String> cityNames = new ArrayList<>();
        try {
            Resources resources = context.getResources();
            InputStream is = resources.openRawResource(resources.getIdentifier("citynames", "raw", context.getPackageName()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String json = stringBuilder.toString();
            Gson gson = new Gson();
            cityNames = gson.fromJson(json, List.class);
            Log.d("AppDatabase", "Loaded cityNames: " + cityNames);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityNames;
    }

    public static List<String> loadCityNames(Context context) {
        return loadCityNamesFromJson(context);
    }
}