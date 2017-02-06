package com.example.nowingo.newsday.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nowingo.newsday.entity.CityBean;
import com.example.nowingo.newsday.entity.TypeBean;
import com.example.nowingo.newsday.quicksidebardemo.model.City;

import java.util.ArrayList;

/**
 * 天气数据库操作类
 *
 */
public class WeatherExpress {
    Context context;//上下文对象
    SQLiteDatabase db;//数据库操作对象
    private static WeatherExpress weatherExpress;//单例模式对象
    private WeatherExpress(Context context) {
        this.context = context;
        WeatherDbHelper wdh=new WeatherDbHelper(context);//创建数据库
        db=wdh.getReadableDatabase();//获得数据库操作对象

    }

    /**
     * 单例模式
     * @return
     */
    public static WeatherExpress getInstance(Context context){
        if(weatherExpress==null){
            weatherExpress=new WeatherExpress(context);
        }
        return weatherExpress;
    }

    /**
     * 存储天气标识
     * @param typeBean
     */
    public void addType(TypeBean typeBean){
        ContentValues cv=new ContentValues();
        cv.put("wid",typeBean.getWid());
        cv.put("weather",typeBean.getWeather());
        db.insert("type",null,cv);
    }
    /**
     * 存储城市信息
     * @param cityBean 需要存储的城市对象
     */
    public void addCity(CityBean cityBean){
        ContentValues cv=new ContentValues();
        cv.put("id",cityBean.getId());
        cv.put("province",cityBean.getProvince());
        cv.put("city",cityBean.getCity());
        cv.put("district",cityBean.getDistrict());
        db.insert("city",null,cv);
    }

    /**
     * 模糊查询城市
     * @param msg  需要查询的值
     * @return  查询出来的城市集合
     */
    public ArrayList<CityBean> getCityMsg(String msg){
        ArrayList<CityBean> list=new ArrayList<CityBean>();//创建一个集合 用于存储查询出来的城市对象
        Cursor cursor=db.rawQuery("select * from city where district like ? ",new String[]{"%"+msg+"%"});
        if(cursor!=null){
            CityBean cityBean=null;//声明一个城市对象
                while(cursor.moveToNext()){//如果游标能够移动往下 代表有数据
                    String id=cursor.getString(cursor.getColumnIndex("id"));
                    String province=cursor.getString(cursor.getColumnIndex("province"));
                    String city=cursor.getString(cursor.getColumnIndex("city"));
                    String district=cursor.getString(cursor.getColumnIndex("district"));
                    cityBean=new CityBean(id,province,city,district);
                //添加到集合中
                    list.add(cityBean);
                }
        }
        return list;
    }

    /**
     * 通过天气标识ID 获得天气中文名
     * @param id  天气的标识ID
     * @return 天气中文名
     */
    public String getWeatherName(String id){
        String weatherName=null;

        Cursor cursor=db.rawQuery("select * from type where wid=?",new String[]{id});
        if(cursor!=null){
            if(cursor.moveToNext()){//由于只有一条数据，所以只需要if
                weatherName=cursor.getString(cursor.getColumnIndex("weather"));
            }
        }
        return weatherName;
    }

    /**
     * 排序查询城市
     * @return  查询出来的城市集合
     */
    public ArrayList<City> getCityAA(){
        ArrayList<City> list=new ArrayList<City>();//创建一个集合 用于存储查询出来的城市对象
        Cursor cursor=db.rawQuery("select * from citymsg order by firstletter asc",null);
        if(cursor!=null){
            City cityBean=null;//声明一个城市对象
            while(cursor.moveToNext()){//如果游标能够移动往下 代表有数据
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String firstletter=cursor.getString(cursor.getColumnIndex("firstletter"));
                cityBean=new City(name,firstletter);
                //添加到集合中
                list.add(cityBean);
            }
        }
        return list;
    }

    /**
     * 排序查询城市
     * @return  查询出来的城市集合
     */
    public ArrayList<City> getCityBB(String msg){
        ArrayList<City> list=new ArrayList<City>();//创建一个集合 用于存储查询出来的城市对象
        Cursor cursor=db.rawQuery("select * from citymsg where name like ? order by firstletter asc",new String[]{"%"+msg+"%"});
        if(cursor!=null){
            City cityBean=null;//声明一个城市对象
            while(cursor.moveToNext()){//如果游标能够移动往下 代表有数据
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String firstletter=cursor.getString(cursor.getColumnIndex("firstletter"));
                cityBean=new City(name,firstletter);
                //添加到集合中
                list.add(cityBean);
            }
        }
        return list;
    }

    /**
     * 存储城市
     * @param cityBean 需要存储的城市对象
     */
    public void addCityAA(City cityBean){
        ContentValues cv=new ContentValues();
        cv.put("name",cityBean.getCityName());
        cv.put("firstletter",cityBean.getFirstLetter());
        db.insert("citymsg",null,cv);
    }

}
