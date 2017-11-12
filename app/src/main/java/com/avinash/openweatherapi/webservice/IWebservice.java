package com.avinash.openweatherapi.webservice;

import com.avinash.openweatherapi.model.WeatherModel;


public interface IWebservice {

    void onSuccess(String tag, WeatherModel model);

    void onFailuer(String tag, String msg);

    void onError();
}
