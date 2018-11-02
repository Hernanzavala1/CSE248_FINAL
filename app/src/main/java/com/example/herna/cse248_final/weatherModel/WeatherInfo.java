package com.example.herna.cse248_final.weatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WeatherInfo {
    @GET("weather?")
    Call<WeatherObject> getWeatherService(@Query("lat") String latitude, @Query("lon") String longitude,
                                          @Query("appid") String ApiKey, @Query("Units") String Units);
}
