package com.example.nowingo.newsday.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class ShareManager {

    //储存是否第一次进入
    public static void saveIsFirst(Context context,boolean flag){
        SharedPreferences sharedPreferences = context.getSharedPreferences("IsFirst",Context.MODE_PRIVATE);//获取共享参数
        SharedPreferences.Editor editor = sharedPreferences.edit();//开启操作器
        editor.putBoolean("isfirst",flag);
        editor.commit();
    }

    //读取是否第一次进入
    public static boolean loadIsFirst(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("IsFirst",Context.MODE_PRIVATE);//获取共享参数
        return sharedPreferences.getBoolean("isfirst",false);
    }

    //储存无图模式
    public static void saveHaveImg(Context context,boolean flag){
        SharedPreferences sharedPreferences = context.getSharedPreferences("HaveImg",Context.MODE_PRIVATE);//获取共享参数
        SharedPreferences.Editor editor = sharedPreferences.edit();//开启操作器
        editor.putBoolean("haveimg",flag);
        editor.commit();
    }

    //读取无图模式
    public static boolean loadHaveImg(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("HaveImg",Context.MODE_PRIVATE);//获取共享参数
        return sharedPreferences.getBoolean("haveimg",false);
    }

    /**
     * 存储上一次退出的时候 查询的城市
     * @param context 上下文对象
     * @param cityName 城市名称
     */
    public static  void saveCity(Context context,String cityName){
        //获得共享参数
        SharedPreferences sharedPreferences=context.getSharedPreferences("city",Context.MODE_PRIVATE);
        //开启操作器
        SharedPreferences.Editor editor=sharedPreferences.edit();
        //执行存储操作
        editor.putString("cityname",cityName);
        //提交
        editor.commit();

    }

    /**
     * 获取上一次存储的城市名称
     * @param context 上下文对象
     * @return  城市名称
     */
    public static String loadCity(Context context){
        String cityName=null;
        //获得共享参数
        SharedPreferences sharedPreferences=context.getSharedPreferences("city",Context.MODE_PRIVATE);
        //取出上一次存储的城市名称
        cityName=sharedPreferences.getString("cityname",null);
        return cityName;
    }
}
