package com.avinash.openweatherapi.webservice;

import com.avinash.openweatherapi.model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIInterface {

//2.5/weather
// q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1

    @GET("2.5/weather")
    Call<WeatherModel> getWeatherByCityName(@Query("q") String cityName, @Query("appid") String appId);

    //    id=2172797&appid=b1b15e88fa797225412429c1c50c122a1
    @GET("2.5/weather")
    Call<WeatherModel> getWeatherByCityId(@Query("id") String id, @Query("appid") String appId);
}
