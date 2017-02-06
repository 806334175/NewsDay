package com.example.nowingo.newsday.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;


import com.example.nowingo.newsday.entity.Channel;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/12/21.
 */
public class NewsViewPagerAdapter extends FragmentStatePagerAdapter {
    //fragment数据源
    ArrayList<Fragment> arrayList;
    //title数据源
    ArrayList<Channel> channelArrayList;

    public NewsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public ArrayList<Fragment> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Fragment> arrayList) {
        this.arrayList = arrayList;
    }

    public ArrayList<Channel> getStringArrayList() {
        return channelArrayList;
    }

    public void setStringArrayList(ArrayList<Channel> stringArrayList) {
        this.channelArrayList = stringArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        fragment = (Fragment) super.instantiateItem(container,position);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //设置title
        return channelArrayList.get(position).getName();
    }


}
