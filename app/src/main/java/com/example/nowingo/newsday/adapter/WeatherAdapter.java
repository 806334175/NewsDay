package com.example.nowingo.newsday.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class WeatherAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;//主内容的数据源
    ArrayList<String> title;//顶部标题的数据源

    public ArrayList<Fragment> getList() {
        return list;
    }

    public void setList(ArrayList<Fragment> list) {
        this.list = list;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public WeatherAdapter(FragmentManager fm) {
        super(fm);
        list=new ArrayList<Fragment>();
        title=new ArrayList<String>();
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);//主内容
    }

    @Override
    public int getCount() {
        return list.size();//返回长度
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);//顶部标题
    }
}
