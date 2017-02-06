package com.example.nowingo.newsday.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class ChannelDbHelper extends SQLiteOpenHelper{
    public ChannelDbHelper(Context context) {
        super(context, "channel.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //储存新闻频道名字及id的表
        db.execSQL("create table channel(channelid text,name text,choose text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
