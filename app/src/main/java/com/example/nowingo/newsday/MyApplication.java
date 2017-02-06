package com.example.nowingo.newsday;

import android.app.Application;

import com.zhy.changeskin.SkinManager;

/**
 * Created by NowINGo on 2017/1/13.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
