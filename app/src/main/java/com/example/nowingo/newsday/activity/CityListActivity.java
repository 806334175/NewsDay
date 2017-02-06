package com.example.nowingo.newsday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.adapter.CityAdapter;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.WeatherExpress;
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
 * Created by NowINGo on 2017/1/11.
 */
public class CityListActivity extends BaseActivity {
    EditText city_ed;
    Button ok_btn;
    ListView lv;
    CityAdapter ca;
    ArrayList<CityBean> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_city_list);
    }

    @Override
    public void inidata() {
        if(WeatherExpress.getInstance(this).getCityMsg("").size()==0){
            getCityMsg();
            getTypeMsg();
        }else{

        }
    }

    @Override
    public void iniview() {
        city_ed= (EditText) findViewById(R.id.city_list_ed);
        lv= (ListView) findViewById(R.id.city_list);
        ok_btn= (Button) findViewById(R.id.city_list_ok_btn);

    }

    @Override
    public void setview() {
        ca=new CityAdapter(this);
        list=new ArrayList<CityBean>();
        list= WeatherExpress.getInstance(this).getCityMsg("");
        ca.setList(list);
        lv.setAdapter(ca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<CityBean>  citylist=ca.getList();//保证我点击的一定是数据源当前加载的
                CityBean  cityBean=citylist.get(i);//获得我点击的哪一个
                city_ed.setText(cityBean.getDistrict());//最后把值传过去

            }
        });

        city_ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s!=null){
                    String msg=s.toString();
                    list=WeatherExpress.getInstance(CityListActivity.this).getCityMsg(msg);//动态的去刷新数据源
                    ca.setList(list);
                    ca.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareManager.saveCity(CityListActivity.this,city_ed.getText().toString().trim());
                Intent intent = new Intent();
                intent.setAction("cityname");
                sendBroadcast(intent);
                finish();
            }
        });

    }

    private void getCityMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String citymsg = HttpConnection.getCityData();
                Message message = new Message();
                message.what = 0;
                message.obj = citymsg;
                handler.sendMessage(message);
            }
        }).start();
    }
    private void getTypeMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String typemsg = HttpConnection.getWeatherType();
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
                                WeatherExpress.getInstance(CityListActivity.this).addCity(citys.get(i));
                            }
                        }else{
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    String typemsg = (String) msg.obj;
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
                                    WeatherExpress.getInstance(CityListActivity.this).addType(typeBean);
                                }
                            }
                        }else{
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
}
