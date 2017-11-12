package com.avinash.openweatherapi.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avinash.openweatherapi.R;
import com.avinash.openweatherapi.adapter.WeatherAdapter;
import com.avinash.openweatherapi.application.Constant;
import com.avinash.openweatherapi.database.DbHelper;
import com.avinash.openweatherapi.model.WeatherModel;
import com.avinash.openweatherapi.webservice.IWebservice;
import com.avinash.openweatherapi.webservice.Webservice;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IWebservice {

    private Context mContext;
    private DbHelper dbHelper;
    private Webservice webservice;
    private WeatherAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Button btnCityName, btnCityId;
    private List<WeatherModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        setClickListeners();

    }

    private void setClickListeners() {
        btnCityName.setOnClickListener(this);
        btnCityId.setOnClickListener(this);
    }

    private void initialization() {
        mContext = this;
        dbHelper = DbHelper.getInstance();
        webservice = Webservice.getInstance();
        webservice.setListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        btnCityId = findViewById(R.id.btnCityId);
        btnCityName = findViewById(R.id.btnCityName);
        progressBar = findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new WeatherAdapter();
        list = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnCityId:

                break;


            case R.id.btnCityName:
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(mContext, "CITY NAME", Toast.LENGTH_SHORT).show();
                webservice.getWetherByCityName(Constant.METHOD_CITY_NAME);
                break;


            default:

                break;

        }
    }

    @Override
    public void onSuccess(String tag, WeatherModel model) {
        progressBar.setVisibility(View.GONE);
        list.clear();
//        saveDataIntoDatabase(model);
        if (tag.equals(Constant.METHOD_CITY_NAME)) {

        } else if (tag.equals(Constant.METHOD_CITY_ID)) {

        }
        if (dbHelper.isReacordAlreadyExist(model)) {
            Toast.makeText(mContext, "Record Found!!", Toast.LENGTH_SHORT).show();
        } else {
            saveDataIntoDatabase(model);
        }

        list.add(model);
        adapter = new WeatherAdapter(mContext, list);
        recyclerView.setAdapter(adapter);
    }

    private void saveDataIntoDatabase(WeatherModel model) {
        if (dbHelper.saveIntoDB(model)) {
            Toast.makeText(mContext, "Record Inserted!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Record Not Inserted!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailuer(String tag, String msg) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
        if (tag.equals(Constant.METHOD_CITY_NAME)) {

        } else if (tag.equals(Constant.METHOD_CITY_ID)) {

        }
    }

    @Override
    public void onError() {

    }
}
