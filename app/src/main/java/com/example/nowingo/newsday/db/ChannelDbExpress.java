package com.example.nowingo.newsday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nowingo.newsday.entity.Channel;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class ChannelDbExpress {
    Context context;
    SQLiteDatabase db;

    public ChannelDbExpress(Context context) {
        this.context = context;
        ChannelDbHelper channelDbHelper = new ChannelDbHelper(context);//实例化数据库
        db = channelDbHelper.getReadableDatabase();//获取数据库操作对象
    }

    //通过频道名字获取id
    public String getIdByName(String name){
        String id = null;
        Cursor cursor = db.rawQuery("select * from channel where name=?",new String[]{name});
        if (cursor != null){
            if (cursor.moveToNext()){
                id = cursor.getString(cursor.getColumnIndex("channelid"));
            }
        }
        return id;
    }


    /**
     * 通过是否选择获得集合
     * @param choose 是否选择
     * @return 频道集合
     */
    public ArrayList<Channel> getNameByChoose(String choose){
        ArrayList<Channel> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from channel where choose=?",new String[]{choose});
        if (cursor != null){
            while (cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex("channelid"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String choosed = cursor.getString(cursor.getColumnIndex("choose"));
                Channel channel = new Channel(id,name,choosed);
                arrayList.add(channel);
            }
        }
        return arrayList;
    }

    /**
     * 修改是否选择
     * @param name 修改的频道名字
     * @param channel 频道类
     */
    public void changeChooseByName(String name,Channel channel){
        ContentValues cv = new ContentValues();
        cv.put("channelid",channel.getChannelid());
        cv.put("name",channel.getName());
        cv.put("choose",channel.getChoose());
        db.update("channel",cv,"name=?",new String[]{name});
    }

    /**
     * 添加频道
     * @param channel 频道类
     */
    public void addChannel(Channel channel){
        ContentValues cv = new ContentValues();
        cv.put("channelid",channel.getChannelid());
        cv.put("name",channel.getName());
        cv.put("choose",channel.getChoose());
        db.insert("channel",null,cv);
    }

}
