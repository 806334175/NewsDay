package com.example.nowingo.newsday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;


import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.adapter.LeadViewPagerAdapter;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.ChannelDbExpress;
import com.example.nowingo.newsday.db.WeatherExpress;
import com.example.nowingo.newsday.entity.Channel;
import com.example.nowingo.newsday.entity.CityBean;
import com.example.nowingo.newsday.entity.TypeBean;
import com.example.nowingo.newsday.http.HttpConnection;
import com.example.nowingo.newsday.manager.ShareManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class LeadActivity extends BaseActivity {
    ViewPager viewPager;
    Button button_skip;
    LeadViewPagerAdapter leadViewPagerAdapter;
    ArrayList<View> arrayList;
    ChannelDbExpress cde;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_lead);
    }

    @Override
    public void inidata() {
        //初始化数据库操作器
        cde = new ChannelDbExpress(LeadActivity.this);
        //判断是否第一次进入
        if (ShareManager.loadIsFirst(this)){
            Intent intent = new Intent(this,LoadingActivity.class);
            startActivity(intent);
            finish();
        }else {
//            getCityMsg();
//            getTypeMsg();
            Channel channel = new Channel("top","头条","true");
            Channel channe2 = new Channel("shehui","社会","true");
            Channel channe3 = new Channel("guonei","国内","true");
            Channel channe4 = new Channel("guoji","国际","true");
            Channel channe5 = new Channel("yule","娱乐","true");
            Channel channe6 = new Channel("tiyu","体育","true");
            Channel channe7 = new Channel("junshi","军事","true");
            Channel channe8 = new Channel("keji","科技","true");
            Channel channe9 = new Channel("caijing","财经","true");
            Channel channel0 = new Channel("shishang","时尚","true");
            cde.addChannel(channel);
            cde.addChannel(channe2);
            cde.addChannel(channe3);
            cde.addChannel(channe4);
            cde.addChannel(channe5);
            cde.addChannel(channe6);
            cde.addChannel(channe7);
            cde.addChannel(channe8);
            cde.addChannel(channe9);
            cde.addChannel(channel0);

        }
        arrayList = new ArrayList<>();
        //加载3个view
        View view1 = LayoutInflater.from(this).inflate(R.layout.lead_page,null);
        View view2 = LayoutInflater.from(this).inflate(R.layout.lead_page,null);
        View view3 = LayoutInflater.from(this).inflate(R.layout.lead_page,null);
        view1.setBackgroundResource(R.drawable.lead_1);
        view2.setBackgroundResource(R.drawable.lead_2);
        view3.setBackgroundResource(R.drawable.lead_3);
        arrayList.add(view1);
        arrayList.add(view2);
        arrayList.add(view3);
    }

    @Override
    public void iniview() {
        viewPager = (ViewPager) findViewById(R.id.activity_lead_vp);
        button_skip = (Button) findViewById(R.id.activity_lead_btn_skip);
    }

    @Override
    public void setview() {
        leadViewPagerAdapter = new LeadViewPagerAdapter(arrayList,this);
        viewPager.setAdapter(leadViewPagerAdapter);
        //给viewpager设置滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //滑动
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            //滑到那个界面
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == arrayList.size() - 1) {
                    button_skip.setVisibility(View.VISIBLE);
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                    alphaAnimation.setDuration(1000);
                    button_skip.startAnimation(alphaAnimation);
                } else {
                    button_skip.setVisibility(View.GONE);
                }
            }

            @Override
            //滑动状态改变
            public void onPageScrollStateChanged(int state) {

            }
        });

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoadingActivity.class);
                finish();
            }
        });

    }

    private void getCityMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String citymsg = HttpConnection.getCityData();
                //解析城市
                try {
                    JSONObject cityJson=new JSONObject(citymsg);
                    if(cityJson.getString("resultcode").equals("200")){
                        JSONArray ar=cityJson.getJSONArray("result");
                        //解析
                        Type listType = new TypeToken<ArrayList<CityBean>>(){}.getType();
                        Gson gson = new Gson();
                        ArrayList<CityBean> citys = gson.fromJson(ar.toString(), listType);
                        //存储到数据库
                        for (int i=0;i<citys.size();i++){
                            WeatherExpress.getInstance(LeadActivity.this).addCity(citys.get(i));
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 0;
                message.obj = citymsg;
                handler.sendMessage(message);
                Log.i("msg","1");
            }
        }).start();
    }
    private void getTypeMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String typemsg = HttpConnection.getWeatherType();
                //解析天气标识
                try {
                    JSONObject typeJson=new JSONObject(typemsg);//先转换为JSONobject对象
                    if(typeJson.getString("resultcode").equals("200")){//代表成功拿到数据
                        JSONArray typeArray=typeJson.getJSONArray("result");//进入了result

                        if(typeArray.length()>0){
                            TypeBean typeBean=null;
                            for (int i=0;i<typeArray.length();i++){
                                JSONObject tmsg=typeArray.getJSONObject(i);//进入了每一个JSONOBJECT对象
                                String wid=tmsg.getString("wid");
                                String weather=tmsg.getString("weather");
                                typeBean=new TypeBean(wid,weather);
                                //存储到数据库
                                WeatherExpress.getInstance(LeadActivity.this).addType(typeBean);
                            }
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = 1;
                message.obj = typemsg;
                handler.sendMessage(message);
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String citymsg = (String) msg.obj;


                    break;
                case 1:
                    String typemsg = (String) msg.obj;

                    break;
            }
        }
    };
}
