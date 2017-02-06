package com.example.nowingo.newsday.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zhy.changeskin.SkinManager;

/**
 * Created by NowINGo on 2017/1/3.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        inidata();
        iniview();
        setview();
        SkinManager.getInstance().register(this);
    }
    /**
     * 加载布局
     */
    public abstract  void setLayout();
    /**
     * 初始化数据
     */
    public abstract  void inidata();
    /**
     * 初始化控件
     */
    public abstract  void iniview();
    /**
     * 设置控件
     */
    public abstract void setview();

    /**
     * 不传递数据的页面跳转
     * @param c  目标页面
     */
    public void startActivity(Class c){
        Intent intent=new Intent(this,c);
        startActivity(intent);
    }

    /**
     * 传递数据的页面跳转
     * @param c  目标页面
     * @param bundle 要传递的数据
     */
    public void startActivity(Class c,Bundle bundle){
        Intent intent=new Intent(this,c);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().unregister(this);
    }
}
