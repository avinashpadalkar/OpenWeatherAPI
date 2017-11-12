package com.avinash.openweatherapi.webservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.avinash.openweatherapi.model.WeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Webservice {

    public IWebservice iWebservice;
    public static Webservice webservice;
    private Context mContext;

    public Webservice(Context mContext) {
        this.mContext = mContext;
    }


    public static Webservice getInstance() {
        if (webservice == null) {
            throw new IllegalStateException("Please call getInitializWebservice()");
        }
        return webservice;
    }

    public static void getInitializeWebservice(Context mContext) {
        if (webservice == null) {
            webservice = new Webservice(mContext);
        }
    }

    public void setListener(IWebservice iWebservice) {
        this.iWebservice = iWebservice;
    }

    public void getWeatherByCityId(final String tag) {
        APIInterface apiInterface1 = APIClient.getInstance().create(APIInterface.class);
        Call<WeatherModel> currentWeatherDataCall1 = apiInterface1.getWeatherByCityId("2172797", "b1b15e88fa797225412429c1c50c122a1");
        currentWeatherDataCall1.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                WeatherModel currentWeatherData = response.body();
                Toast.makeText(mContext, "" + currentWeatherData.cod, Toast.LENGTH_SHORT).show();
                iWebservice.onSuccess(tag, currentWeatherData);
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.e("onFailure===", "" + t.toString());
                iWebservice.onFailuer(tag, "Error Message");
            }
        });

    }

    public void getWetherByCityName(final String tag) {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);

        Call<WeatherModel> currentWeatherDataCall = apiInterface.getWeatherByCityName("London,uk", "b1b15e88fa797225412429c1c50c122a1");
        currentWeatherDataCall.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                Log.e("onResponse===", "" + response.body());
                WeatherModel currentWeatherData = response.body();
                Toast.makeText(mContext, "" + currentWeatherData.cod, Toast.LENGTH_SHORT).show();
                iWebservice.onSuccess(tag, currentWeatherData);
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                Log.e("onFailure===", "" + t.toString());
                iWebservice.onFailuer(tag, "Error Message");
            }
        });
    }


}
