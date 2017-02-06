package com.example.nowingo.newsday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class WeatherDbHelper extends SQLiteOpenHelper {
    public WeatherDbHelper(Context context) {
        super(context, "weather.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //储存天气标识的表
        db.execSQL("create table type(wid text, weather text)");
        //存储城市信息列表
        db.execSQL("create table city(id  text,province text,city text,district text)");
        //存储城市信息列表及首字母
        db.execSQL("create table citymsg(name text,firstletter text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
