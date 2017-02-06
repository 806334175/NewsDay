package com.example.nowingo.newsday.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.entity.Weather;
import com.example.nowingo.newsday.entity.WeatherBean;
import com.example.nowingo.newsday.view.CommonChartView;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NowINGo on 2017/1/12.
 */
public class ChartsActivity extends BaseActivity {
    CommonChartView commonChartView;
    String str;
    Weather weatherBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_charts);
    }

    @Override
    public void inidata() {
        Bundle bundle = getIntent().getExtras();
        str = bundle.getString("msg");
    }

    @Override
    public void iniview() {
        commonChartView = (CommonChartView) findViewById(R.id.activity_charts);
    }

    @Override
    public void setview() {
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
        set();
    }
    private void set(){
        ArrayList<String> arrTitle = new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            arrTitle.add(weatherBean.getDayList().get(i).getDaytime());
        }
        commonChartView.setTitleXList(arrTitle);

        List<List<Float>> list = new ArrayList<>();
        List<Float> arrPoint = new ArrayList<>();
        for (int i = 0; i <6 ; i++) {
            String str = weatherBean.getDayList().get(i).getDay_air_temperature();
            arrPoint.add(Float.valueOf(str));
        }
        list.add(arrPoint);
        commonChartView.setPointList(list);
        List<Integer> listcolor = new ArrayList<>();
        listcolor.add(Color.BLUE);
        commonChartView.setLineColorList(listcolor);
    }
}
