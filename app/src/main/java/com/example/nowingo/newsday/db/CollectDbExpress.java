package com.example.nowingo.newsday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.nowingo.newsday.entity.Collect;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/5.
 */
public class CollectDbExpress {
    Context context;
    SQLiteDatabase db;

    public CollectDbExpress(Context context) {
        this.context = context;
        CollectDbHelper collectDbHelper = new CollectDbHelper(context);
        db = collectDbHelper.getReadableDatabase();
    }

    public void add(Collect collect){
        ContentValues cv = new ContentValues();
        cv.put("msg",collect.getMsg());
        cv.put("id",collect.getFrom());
        db.insert("collect",null,cv);
    }

    public void delete(String msg){
        db.delete("collect","msg=?",new String[]{msg});
    }

    public ArrayList<Collect> findall(){
        ArrayList<Collect> arrayList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from collect",null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                String msg = cursor.getString(cursor.getColumnIndex("msg"));
                String from = cursor.getString(cursor.getColumnIndex("id"));
                Collect collect = new Collect(msg,from);
                arrayList.add(collect);
            }
        }
        return arrayList;
    }
    public boolean findone(String msg){
        boolean flag = false;
        Cursor cursor = db.rawQuery("select * from collect where msg=?",new String[]{msg});
        if (cursor!=null){
            if (cursor.moveToNext()){
                flag = true;
            }
        }
        return flag;
    }
}
