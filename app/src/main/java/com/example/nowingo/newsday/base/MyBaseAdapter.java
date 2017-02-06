package com.example.nowingo.newsday.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * 自定义基类适配器
 *
 */
public abstract class MyBaseAdapter<Data> extends BaseAdapter {
    public Context context;
    public LayoutInflater layoutInflater;//布局构造器
    public ArrayList<Data> list;//数据源


    public MyBaseAdapter(Context context) {
        this.context = context;
        layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list=new ArrayList<Data>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<Data> getList() {
        return list;
    }

    public void setList(ArrayList<Data> list) {
        this.list = list;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return setView(position,convertView,parent);
    }
    public abstract  View setView(int position, View convertView, ViewGroup parent);
}
