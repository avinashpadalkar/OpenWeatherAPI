package com.avinash.openweatherapi.webservice;

import com.avinash.openweatherapi.application.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class APIClient {

    public static Retrofit retrofit = null;

    public static Retrofit getInstance() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(Constant.MAIN_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;
    }
}
