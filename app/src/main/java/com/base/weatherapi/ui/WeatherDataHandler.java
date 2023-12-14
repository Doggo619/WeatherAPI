//package com.base.weatherapi.ui;
//
//import android.view.View;
//
//import com.base.weatherapi.R;
//import com.base.weatherapi.response.WeatherResponse;
//
//public class WeatherDataHandler {
//    public static void displayWeatherData(MainActivity activity, WeatherResponse weatherResponse) {
//        if (activity.validateCityName(activity.etCity.getText().toString().trim())) {
//            activity.weatherCard.setVisibility(View.VISIBLE);
//            activity.errorCard.setVisibility(View.GONE);
//
//            activity.tvCityName.setText("Weather of " + activity.etCity.getText().toString().trim());
//
//            if (weatherResponse.getWeatherList() != null && !weatherResponse.getWeatherList().isEmpty()) {
//                WeatherResponse.Weather weather = weatherResponse.getWeatherList().get(0);
//                String weatherCondition = weather.getMainCondition();
//
//                activity.weatherImage.setVisibility(View.VISIBLE);
//                switch (weatherCondition) {
//                    case "Clear":
//                        activity.weatherImage.setImageResource(R.drawable.clear);
//                        break;
//                    case "Clouds":
//                        activity.weatherImage.setImageResource(R.drawable.clouds);
//                        break;
//                    case "Rain":
//                        activity.weatherImage.setImageResource(R.drawable.rain);
//                        break;
//                    case "Drizzle":
//                        activity.weatherImage.setImageResource(R.drawable.drizzle);
//                        break;
//                    case "Thunderstorm":
//                        activity.weatherImage.setImageResource(R.drawable.thunderstorm);
//                        break;
//                    case "Snow":
//                        activity.weatherImage.setImageResource(R.drawable.snow);
//                        break;
//                    case "Mist":
//                        activity.weatherImage.setImageResource(R.drawable.mist);
//                        break;
//                    case "Fog":
//                        activity.weatherImage.setImageResource(R.drawable.fog);
//                        break;
//                    case "Smoke":
//                        activity.weatherImage.setImageResource(R.drawable.smoke);
//                        break;
//                    case "Haze":
//                        activity.weatherImage.setImageResource(R.drawable.haze);
//                        break;
//                    case "Dust":
//                        activity.weatherImage.setImageResource(R.drawable.dust);
//                        break;
//                    case "Sand":
//                        activity.weatherImage.setImageResource(R.drawable.sand);
//                        break;
//                    case "Ash":
//                        activity.weatherImage.setImageResource(R.drawable.ash);
//                        break;
//                    case "Squall":
//                        activity.weatherImage.setImageResource(R.drawable.squall);
//                        break;
//                    case "Tornado":
//                        activity.weatherImage.setImageResource(R.drawable.tornado);
//                        break;
//                    default:
//                        activity.weatherImage.setImageResource(R.drawable.clear);
//                        break;
//                }
//                activity.tvWeatherCondition.setText("Weather Condition: " + weatherCondition);
//            }
//
//            double temperatureKelvin = weatherResponse.getMain().getTemp();
//            double temperatureCelsius = temperatureKelvin - 273.15;
//            String temperature = String.format("%.2f", temperatureCelsius);
//            activity.tvTemperature.setText("Temperature: " + temperature + "°C");
//
//            int humidity = weatherResponse.getMain().getHumidity();
//            activity.tvHumidity.setText("Humidity: " + humidity + "%");
//
//            int windSpeed = (int) (weatherResponse.getWind().getSpeed() * 3.6);
//            activity.tvWindSpeed.setText("Wind Speed: " + windSpeed + " km/hr");
//
//            String rainChances;
//            if (weatherResponse.getRain() != null && weatherResponse.getRain().getOneHour() != 0) {
//                double rain1h = weatherResponse.getRain().getOneHour();
//                rainChances = String.format("%.2f", rain1h) + "%";
//                activity.tvRain.setText("Rain Chances: " + rainChances);
//            } else {
//                rainChances = "0%";
//                activity.tvRain.setText("Rain Chances: " + rainChances);
//            }
//        }
//    }
//}
