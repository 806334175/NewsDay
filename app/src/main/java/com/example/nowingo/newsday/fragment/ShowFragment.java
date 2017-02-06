package com.example.nowingo.newsday.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.entity.Weather;
import com.example.nowingo.newsday.entity.WeatherBean;


/**
 *
 */
public class ShowFragment extends Fragment {
    TextView show_tv_temperature;
    TextView show_tv_weather;
    TextView show_tv_direction;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        show_tv_temperature= (TextView) view.findViewById(R.id.show_tv_temperature);
        show_tv_weather= (TextView) view.findViewById(R.id.show_tv_weather);
        show_tv_direction= (TextView) view.findViewById(R.id.show_tv_direction);
        getData();

    }
    public void getData(){
        Bundle bundle=getArguments();//获得上级页面给过来的数据
        Weather.DayListBean futureBean = (Weather.DayListBean) bundle.getSerializable("data");
        show_tv_temperature.setText("温度:"+futureBean.getDay_air_temperature()+"℃");
        show_tv_weather.setText("天气:"+futureBean.getDay_weather());
        show_tv_direction.setText("风向:"+futureBean.getDay_wind_direction());
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show,null);
    }
}
