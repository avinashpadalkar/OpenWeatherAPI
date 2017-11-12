package com.avinash.openweatherapi.application;

import android.app.Application;

import com.avinash.openweatherapi.database.DbHelper;
import com.avinash.openweatherapi.webservice.Webservice;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Webservice.getInitializeWebservice(getApplicationContext());
        DbHelper.initDB(getApplicationContext());
    }
}
