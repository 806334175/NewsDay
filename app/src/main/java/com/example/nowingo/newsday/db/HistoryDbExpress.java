package com.example.nowingo.newsday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nowingo.newsday.entity.History;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/5.
 */
public class HistoryDbExpress {
    Context context;
    SQLiteDatabase db;

    public HistoryDbExpress(Context context) {
        this.context = context;
        HistoryDbHelper historyDbHelper = new HistoryDbHelper(context);//实例化数据库
        db = historyDbHelper.getReadableDatabase();//获取数据库操作对象
    }

    public void add(History history){
        ContentValues cv = new ContentValues();
        cv.put("name",history.getName());
        cv.put("date",history.getDate());
        db.insert("history",null,cv);
    }

    public ArrayList<History> findall(){
        ArrayList<History> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from history",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                History history = new History(name,date);
                arrayList.add(history);
            }
        }
        return arrayList;
    }

    public void delete(String name){
        db.delete("history","name=?",new String[]{name});
    }

    public void deleteall(){
        db.delete("history",null,null);
    }


    public boolean ishave(String name){
        boolean flag = false;
        Cursor cursor = db.rawQuery("select * from history where name=?",new String[]{name});
        if (cursor!=null){
            if (cursor.moveToNext()){
                flag = true;
            }
        }
        return flag;
    }
}
