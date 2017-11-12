package com.avinash.openweatherapi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.avinash.openweatherapi.model.WeatherModel;



public class DbHelper extends SQLiteOpenHelper {
    private static DbHelper sInstance;
    private static final String DB_NAME = "MY_DB";
    private static final String TABLE_NAME = "MY_INFO";
    private String CREATE_TABLE = "create table " + TABLE_NAME + " (id TEXT PRIMARY KEY, name TEXT)";
    private SQLiteDatabase mDatabase;
    private int openCounter = 0;
    private static final String TAG = DbHelper.class.getSimpleName() + "=== ";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    public static synchronized DbHelper getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(TAG +
                    " db not initialized, Please call \"initDB()\" first");
        }
        return sInstance;
    }

    public static synchronized void initDB(Context context) {
        if (sInstance == null) {
            sInstance = new DbHelper(context);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public synchronized boolean insertRecord(WeatherModel cityDatabase) {
        boolean isInserted = false;
        long count = -1;
        mDatabase = writeData();
        mDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            /*values.put("name", personInfo.getFullName());
            values.put("email", personInfo.getEmail());
            values.put("age", personInfo.getAge());
            values.put("mobile_no", personInfo.getMobile_no());*/
            count = mDatabase.insert(TABLE_NAME, null, values);

            if (count > -1) {
                isInserted = true;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            mDatabase.endTransaction();
        } finally {
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
            closeDatabase();
        }
        return isInserted;
    }

    public synchronized SQLiteDatabase writeData() {
        openCounter++;
        if (openCounter == 1) {
            Log.i(TAG, "new write open");
            mDatabase = sInstance.getWritableDatabase();
        } else {
            Log.i(TAG, "old write open");
        }
        return mDatabase;
    }

    public synchronized SQLiteDatabase readData() {
        openCounter++;
        if (openCounter == 1) {
            Log.i(TAG, "new read open");
            mDatabase = sInstance.getReadableDatabase();
        } else {
            Log.i(TAG, "old read open");
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        openCounter--;
        if (openCounter == 0) {
            Log.i(TAG, "db closed");
            mDatabase.close();
        } else {
            Log.i(TAG, "db not closed. Open Connections == " + openCounter);
        }
    }

    public boolean saveIntoDB(WeatherModel currentWeatherData) {
        boolean isInserted = false;
        long count = -1;
        mDatabase = writeData();
        mDatabase.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("id", currentWeatherData.getId());
            values.put("name", currentWeatherData.getName());
            count = mDatabase.insert(TABLE_NAME, null, values);

            if (count > -1) {
                isInserted = true;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            mDatabase.endTransaction();
        } finally {
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
            closeDatabase();
        }
        return isInserted;
    }

    public boolean isReacordAlreadyExist(WeatherModel currentWeatherData) {
        boolean isRecordFound = false;
        long count = -1;
        mDatabase = writeData();
        mDatabase.beginTransaction();
        String rawQuery = "select * from " + TABLE_NAME + " Where id = " + currentWeatherData.getId();
        String resultId = "";
        try {
            /*ContentValues values = new ContentValues();
            values.put("id", currentWeatherData.getId());
            values.put("name", currentWeatherData.getName());
            count = mDatabase.insert(TABLE_NAME, null, values);*/
            Cursor cursor = mDatabase.rawQuery(rawQuery, null);

            if (cursor.moveToFirst()) {
                do {

                    resultId = cursor.getString(cursor.getColumnIndex("id"));

                } while (cursor.moveToNext());
            }


            if (resultId != null) {
                isRecordFound = true;
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
            mDatabase.endTransaction();
        } finally {
            mDatabase.setTransactionSuccessful();
            mDatabase.endTransaction();
            closeDatabase();
        }
        return isRecordFound;
    }
}
