package com.example.nowingo.newsday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.adapter.WeatherAdapter;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.entity.Weather;
import com.example.nowingo.newsday.entity.WeatherBean;
import com.example.nowingo.newsday.fragment.ShowFragment;
import com.example.nowingo.newsday.manager.ToolbarManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NowINGo on 2017/1/11.
 */
public class WeatherActivity extends BaseActivity {
    ViewPager vp;
    TabLayout tab;
    WeatherAdapter wa;//适配器
    ArrayList<String> title;//顶部标签
    ArrayList<Fragment> list;//主内容
    FragmentManager fm;
    View view;

    Weather weatherBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_weather);
    }

    @Override
    public void inidata() {
        title=new ArrayList<String>();
        list=new ArrayList<Fragment>();
        fm = getSupportFragmentManager();
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_weather_toolbar);
        vp= (ViewPager) findViewById(R.id.weather_vp);
        wa=new WeatherAdapter(fm);
        tab= (TabLayout) findViewById(R.id.weather_tab);
        vp.setAdapter(wa);
        vp.setOffscreenPageLimit(7);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);//设置顶部允许滑动
        tab.setupWithViewPager(vp);
    }

    @Override
    public void setview() {
        ToolbarManager.setTitle(view,this,"未来七天");
        Bundle bundle = getIntent().getExtras();
        String str = bundle.getString("msg");
        jieXi(str);
    }
    public void jieXi(String weathermsg){
        try {
            JSONObject jo=new JSONObject(weathermsg);
            if(jo.getString("showapi_res_code").equals("0")){
                JSONObject resuly=jo.getJSONObject("showapi_res_body");
                weatherBean=new Gson().fromJson(resuly.toString(),Weather.class);//一键解析
            }else{
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setdata();

    }
    void setdata(){
//        WeatherBean.TodayBean todayBean=weatherBean.getToday();//获取今天的数据
//        title.add(todayBean.getWeek());//设置顶部标签
//        ShowFragment showFragment=new ShowFragment();
//        Bundle bundle=new Bundle();
//        bundle.putSerializable("data",todayBean);//第一个标签 对应的是今日天气
//        showFragment.setArguments(bundle);
//        list.add(showFragment);
        List<Weather.DayListBean> futureBeanList=weatherBean.getDayList();//获得未来几天的数据源
        for (int i=0;i<futureBeanList.size();i++){
            Weather.DayListBean futureBean=futureBeanList.get(i);//未来每一个的数据源
            title.add(futureBean.getDaytime());//添加了标签
            ShowFragment showFragment1=new ShowFragment();
            Bundle  bundle1=new Bundle();
            bundle1.putSerializable("data",futureBean);
            showFragment1.setArguments(bundle1);
            list.add(showFragment1);//主页面的数据
        }
        wa.setTitle(title);
        wa.setList(list);
        wa.notifyDataSetChanged();//刷新一次
    }
}
