package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class LeadViewPagerAdapter extends PagerAdapter {
    //数据源
    ArrayList<View> arrayList;
    //上下文
    Context context;

    public LeadViewPagerAdapter(ArrayList<View> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = arrayList.get(position);
        //向viewpager中添加布局
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = arrayList.get(position);
        //移除布局
        container.removeView(view);
    }
}
