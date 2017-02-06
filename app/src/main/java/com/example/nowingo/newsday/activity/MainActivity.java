package com.example.nowingo.newsday.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.WeatherExpress;
import com.example.nowingo.newsday.entity.CityBean;
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.entity.TypeBean;
import com.example.nowingo.newsday.entity.Weather;
import com.example.nowingo.newsday.entity.WeatherBean;
import com.example.nowingo.newsday.fragment.HotTopicFragment;
import com.example.nowingo.newsday.fragment.FindFragment;
import com.example.nowingo.newsday.fragment.NewsFragment;
import com.example.nowingo.newsday.http.HttpConnection;
import com.example.nowingo.newsday.http.HttpEntity;
import com.example.nowingo.newsday.manager.ShareManager;
import com.example.nowingo.newsday.quicksidebardemo.CityActivity;
import com.example.nowingo.newsday.quicksidebardemo.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    /**
     *
     */
    private class LogInListener implements IUiListener {

        @Override
        public void onComplete(Object o) {
            Toast.makeText(MainActivity.this, "授权成功！", Toast.LENGTH_LONG).show();
            System.out.println("o.toString() ------------------------->        " + o.toString());

            JSONObject jsonObject = (JSONObject) o;

            //设置openid和token，否则获取不到下面的信息
            initOpenidAndToken(jsonObject);
            //获取QQ用户的各信息
            getUserInfo();
        }

        @Override
        public void onError(UiError uiError) {

            Toast.makeText(MainActivity.this, "授权出错！", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "授权取消！", Toast.LENGTH_LONG).show();
        }
    }
    //需要腾讯提供的一个Tencent类
    private Tencent mTencent;
    //还需要一个IUiListener 的实现类（LogInListener implements IUiListener）
    private LogInListener mListener;

    /**
     *
     */

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    FragmentManager fragmentManager;

    NewsFragment fragment_news;
    HotTopicFragment fragment_collect;
    FindFragment fragment_find;

    LinearLayout linearLayout_news;
    LinearLayout linearLayout_collect;
    LinearLayout linearLayout_find;

    ImageView imageView_news;
    ImageView imageView_collect;
    ImageView imageView_find;
    CircleImageView circleImageView_user;

    TextView textView_news;
    TextView textView_collect;
    TextView textView_find;
    TextView textView_login;
    TextView textView_city;
    TextView textView_weather;
    TextView textView_changecity;
    TextView textView_express;

    LinearLayout linearLayout_left_collect;
    LinearLayout linearLayout_left_aboutus;
    LinearLayout linearLayout_left_setting;



    String nowweather;
    MyReciver myReciver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void inidata() {
//        if (WeatherExpress.getInstance(this).getCityMsg("").size() == 0){
//            getTypeMsg();
//            getCityMsg();
//        }
        //在Oncreate里面注册
        myReciver=new MyReciver();
        //绑定意图过滤器
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("cityname");
        //执行注册
        registerReceiver(myReciver,intentFilter);


        /**
         *
         */
        //首先需要用APP ID 获取到一个Tencent实例
        mTencent = Tencent.createInstance("100371282", this.getApplicationContext());
        //初始化一个IUiListener对象，在IUiListener接口的回调方法中获取到有关授权的某些信息
        // （千万别忘了覆写onActivityResult方法，否则接收不到回调）
        mListener = new LogInListener();
        /**
         *
         */

        //将是否第一次登陆设为true
        ShareManager.saveIsFirst(this,true);
    }

    @Override
    public void iniview() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawerlayout);

        linearLayout_news = (LinearLayout) findViewById(R.id.activity_main_bottom_ll_news);
        linearLayout_collect = (LinearLayout) findViewById(R.id.activity_main_bottom_ll_collect);
        linearLayout_find = (LinearLayout) findViewById(R.id.activity_main_bottom_ll_find);

        imageView_news = (ImageView) findViewById(R.id.activity_main_bottom_ll_news_img);
        imageView_collect = (ImageView) findViewById(R.id.activity_main_bottom_ll_collect_img);
        imageView_find = (ImageView) findViewById(R.id.activity_main_bottom_ll_find_img);
        circleImageView_user = (CircleImageView) findViewById(R.id.activity_main_user_img);

        textView_news = (TextView) findViewById(R.id.activity_main_bottom_ll_news_tv);
        textView_collect = (TextView) findViewById(R.id.activity_main_bottom_ll_collect_tv);
        textView_find = (TextView) findViewById(R.id.activity_main_bottom_ll_find_tv);
        textView_login = (TextView) findViewById(R.id.activity_main_drawerlayout_left_login_tv);
        textView_city = (TextView) findViewById(R.id.activity_main_drawerlayout_left_city);
        textView_weather = (TextView) findViewById(R.id.activity_main_drawerlayout_left_weather);
        textView_changecity = (TextView) findViewById(R.id.activity_main_drawerlayout_left_changecity);
        textView_express = (TextView) findViewById(R.id.activity_main_drawerlayout_left_express);


        linearLayout_left_collect = (LinearLayout) findViewById(R.id.activity_main_drawerlayout_left_collect);
        linearLayout_left_aboutus = (LinearLayout) findViewById(R.id.activity_main_drawerlayout_left_aboutus);
        linearLayout_left_setting = (LinearLayout) findViewById(R.id.activity_main_drawerlayout_left_setting);
    }

    @Override
    public void setview() {
        setToolbarAndDrawerLayout();
        setDefault();
        textView_login.setOnClickListener(this);
        linearLayout_news.setOnClickListener(this);
        linearLayout_collect.setOnClickListener(this);
            linearLayout_find.setOnClickListener(this);
        linearLayout_left_collect.setOnClickListener(this);
        linearLayout_left_aboutus.setOnClickListener(this);
        linearLayout_left_setting.setOnClickListener(this);
        textView_changecity.setOnClickListener(this);
        textView_weather.setOnClickListener(this);
        textView_express.setOnClickListener(this);
        textView_city.setOnClickListener(this);

    }

    /**
     * 设置主界面的Toorbar和DrawerLayout联动
     */
    private void setToolbarAndDrawerLayout(){
        toolbar.setTitle("NewsDay");//设置Toolbar标题
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        //初始化状态
        actionBarDrawerToggle.syncState();
        //加载给抽屉
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //当DrawerLayout滑动出来的时候，默认是会给侧边栏下面部分的界面蒙上一层阴影,这里设为透明
        //drawerLayout.setScrimColor(Color.TRANSPARENT);

        //给菜单设置监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.toolbar_menu_right_share:
                        showShare();
                        //Toast.makeText(CityActivity.this,"点了分享",Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
    //创建Toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    /**
     * 设置界面的默认状态
     */
    private void setDefault(){
        imageView_news.setBackgroundResource(R.drawable.new_selected);
        textView_news.setTextColor(android.graphics.Color.parseColor("#4a9bc2"));
        //获得管理器
        fragmentManager  = getSupportFragmentManager();
        //开启事务管理
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //获得3个碎片
        fragment_news = new NewsFragment();
        fragment_collect = new HotTopicFragment();
        fragment_find = new FindFragment();
        //将3个碎片添加到事务管理中
        fragmentTransaction.add(R.id.activity_main_fragment,fragment_news);
        fragmentTransaction.add(R.id.activity_main_fragment,fragment_collect);
        fragmentTransaction.add(R.id.activity_main_fragment,fragment_find);
        //隐藏3个碎片
        fragmentTransaction.hide(fragment_news).hide(fragment_collect).hide(fragment_find);
        //显示一个
        fragmentTransaction.show(fragment_news);
        //提交
        fragmentTransaction.commit();
        setWeather();

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
                            WeatherExpress.getInstance(MainActivity.this).addCity(citys.get(i));
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (WeatherExpress.getInstance(MainActivity.this).getCityAA().size() == 0){
                    AAA();
                }
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
                                WeatherExpress.getInstance(MainActivity.this).addType(typeBean);
                            }
                        }
                    }else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    /**
     * 设置天气
     *
     */
    private void setWeather(){
        String city = ShareManager.loadCity(this);
        if (city == null){
            textView_weather.setText("请选择城市");
        }else {
            textView_city.setText(city);
            getWeather(city);
        }
    }

    /**
     * 获得天气信息
     * http://route.showapi.com/9-9
     */
    private void getWeather(final String city){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                nowweather = HttpConnection.getCityWeather(city);
//                Message message = new Message();
//                message.what = 0;
//                message.obj = nowweather;
//                handler.sendMessage(message);
//            }
//        }).start();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://route.showapi.com/9-9", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("msg","1111111");
                nowweather = s;
                //Weather weather = new Gson().fromJson(s,Weather.class);
                Message message = new Message();
                message.what = 0;
                message.obj = nowweather;
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<>();
//                map.put("showapi_appid", HttpEntity.APP_ID);
//                map.put("channelName","游戏焦点");
//                map.put("showapi_sign", HttpEntity.HTTP_KEY);
//                return map;
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("showapi_appid", HttpEntity.APP_ID);
                    map.put("showapi_sign", HttpEntity.HTTP_KEY);
                    map.put("area", city);
                    return map;
                }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
        requestQueue.start();
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.i("msg","222222");
                    String weather = (String) msg.obj;
                    Log.i("msg",weather);
                    try {
                        JSONObject jo=new JSONObject(weather);
                        if(jo.getString("showapi_res_code").equals("0")){
                            JSONObject resuly=jo.getJSONObject("showapi_res_body");
                            Weather weathermsg=new Gson().fromJson(resuly.toString(),Weather.class);//一键解析
                            String str = weathermsg.getDayList().get(0).getDay_weather()+"  "+weathermsg.getDayList().get(0).getDay_air_temperature()+"℃";
                            textView_weather.setText(str);
                        }else{
                            Toast.makeText(MainActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //资讯监听
            case R.id.activity_main_bottom_ll_news:
                imageView_news.setBackgroundResource(R.drawable.new_selected);
                textView_news.setTextColor(android.graphics.Color.parseColor("#4a9bc2"));

                imageView_collect.setBackgroundResource(R.drawable.collect_unselected);
                textView_collect.setTextColor(android.graphics.Color.parseColor("#000000"));
                imageView_find.setBackgroundResource(R.drawable.find_defult);
                textView_find.setTextColor(android.graphics.Color.parseColor("#000000"));

                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                fragmentTransaction1.hide(fragment_news).hide(fragment_collect).hide(fragment_find);
                fragmentTransaction1.show(fragment_news);
                fragmentTransaction1.commit();
                break;
            //热点监听
            case R.id.activity_main_bottom_ll_collect:
                imageView_collect.setBackgroundResource(R.drawable.collect_selected);
                textView_collect.setTextColor(android.graphics.Color.parseColor("#4a9bc2"));

                imageView_news.setBackgroundResource(R.drawable.new_unselected);
                textView_news.setTextColor(android.graphics.Color.parseColor("#000000"));
                imageView_find.setBackgroundResource(R.drawable.find_defult);
                textView_find.setTextColor(android.graphics.Color.parseColor("#000000"));

                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.hide(fragment_news).hide(fragment_collect).hide(fragment_find);
                fragmentTransaction2.show(fragment_collect);
                fragmentTransaction2.commit();
                break;
            //搜索监听
            case R.id.activity_main_bottom_ll_find:
                imageView_find.setBackgroundResource(R.drawable.find_selected);
                textView_find.setTextColor(android.graphics.Color.parseColor("#4a9bc2"));

                imageView_news.setBackgroundResource(R.drawable.new_unselected);
                textView_news.setTextColor(android.graphics.Color.parseColor("#000000"));
                imageView_collect.setBackgroundResource(R.drawable.collect_unselected);
                textView_collect.setTextColor(android.graphics.Color.parseColor("#000000"));

                FragmentTransaction fragmentTransaction3 = fragmentManager.beginTransaction();
                fragmentTransaction3.hide(fragment_news).hide(fragment_collect).hide(fragment_find);
                fragmentTransaction3.show(fragment_find);
                fragmentTransaction3.commit();
                break;
            case R.id.activity_main_drawerlayout_left_collect:
                startActivity(CollectActivity.class);
                break;
            case R.id.activity_main_drawerlayout_left_aboutus:
                startActivity(AboutUsActivity.class);

                break;
            case R.id.activity_main_drawerlayout_left_setting:
                startActivity(SettingActivity.class);

                break;
            case R.id.activity_main_drawerlayout_left_login_tv:
                if (textView_login.getText().toString().equals("立即登录")) {

                    /**
                     *
                     */
                    //调用QQ登录，用IUiListener对象作参数（点击登录按钮时执行以下语句）
                    if (!mTencent.isSessionValid()) {
                        mTencent.login(MainActivity.this, "all", mListener);
                    }
                    /**
                     *
                     */

                    //Toast.makeText(CityActivity.this,"登录",Toast.LENGTH_SHORT).show();
                }else if (textView_login.getText().toString().equals("退出登录")){
                    /**
                     *
                     */
                    //退出QQ登录
                    mTencent.logout(MainActivity.this);
                    /**
                     *
                     */
                    circleImageView_user.setImageResource(R.drawable.login_defult_img);
                    textView_login.setText("立即登录");
                }
                break;
            case R.id.activity_main_drawerlayout_left_changecity:
//                startActivity(CityListActivity.class);
                startActivity(CityActivity.class);
                break;
            case R.id.activity_main_drawerlayout_left_weather:
                if (nowweather!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", nowweather);
                    startActivity(WeatherActivity.class, bundle);
                }
                break;
            case R.id.activity_main_drawerlayout_left_express:
                startActivity(ExpressActivity.class);
                break;
            case R.id.activity_main_drawerlayout_left_city:
                if (nowweather!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", nowweather);
                    startActivity(ChartsActivity.class, bundle);
                }
                break;
        }
    }

    /**
     * 创建有序数据库
     */

    public void AAA(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<CityBean> arrayList = new ArrayList<CityBean>();
                arrayList = WeatherExpress.getInstance(MainActivity.this).getCityMsg("");
                for (int i = 0; i <arrayList.size() ; i++) {
                    String name = arrayList.get(i).getDistrict();
                    String firstletter = getPinYinHeadChar(name.substring(0,1));
                    City c = new City(name,firstletter);
                    WeatherExpress.getInstance(MainActivity.this).addCityAA(c);
                }
                Log.i("msg","完成");

            }
        }).start();
    }
    /**
     * 提取每个汉字的首字母
     * @param str
     * @return String
     */
    public String getPinYinHeadChar(String str) {
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += String.valueOf(pinyinArray[0].charAt(0)).toUpperCase();
            } else {
                convert += String.valueOf(word).toUpperCase();
            }
        }
        return convert;
    }

    //自定义的广播接收器
    class MyReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //是你响应了这个广播 要执行操作
            String Action=intent.getAction();//获得Action
            if(Action.equals("cityname")){//判断Action
                setWeather();
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciver);
    }

    /**
     *
     */
    //确保能接收到回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, mListener);
    }
    /**
     *
     */

    /**
     *
     */
    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");

            mTencent.setAccessToken(token, expires);
            mTencent.setOpenId(openid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */

    /**
     *
     */
    private void getUserInfo() {

        //sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
        QQToken mQQToken = mTencent.getQQToken();
        UserInfo userInfo = new UserInfo(MainActivity.this, mQQToken);
        userInfo.getUserInfo(new IUiListener() {
                                 @Override
                                 public void onComplete(final Object o) {
                                     JSONObject userInfoJson = (JSONObject) o;
                                     Message msgNick = new Message();
                                     msgNick.what = 0;//昵称
                                     try {
                                         msgNick.obj = userInfoJson.getString("nickname");//直接传递一个昵称的内容过去
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }
                                     mHandler.sendMessage(msgNick);
                                     //子线程 获取并传递头像图片，由Handler更新
                                     new Thread(new Runnable() {
                                         @Override
                                         public void run() {
                                             Bitmap bitmapHead = null;
                                             if (((JSONObject) o).has("figureurl")) {
                                                 try {
                                                     String headUrl = ((JSONObject) o).getString("figureurl_qq_2");
                                                     bitmapHead = Util.getbitmap(headUrl);
                                                 } catch (JSONException e) {
                                                     e.printStackTrace();
                                                 }
                                                 Message msgHead = new Message();
                                                 msgHead.what = 1;
                                                 msgHead.obj = bitmapHead;
                                                 mHandler.sendMessage(msgHead);
                                             }
                                         }
                                     }).start();

                                 }

                                 @Override
                                 public void onError(UiError uiError) {
                                     Log.e("GET_QQ_INFO_ERROR", "获取qq用户信息错误");
                                     Toast.makeText(MainActivity.this, "获取qq用户信息错误", Toast.LENGTH_SHORT).show();
                                 }

                                 @Override
                                 public void onCancel() {
                                     Log.e("GET_QQ_INFO_CANCEL", "获取qq用户信息取消");
                                     Toast.makeText(MainActivity.this, "获取qq用户信息取消", Toast.LENGTH_SHORT).show();
                                 }
                             }
        );
    }

    /**
     *
     */

    /**
     *
     */
    public static class Util {
        public static String TAG="UTIL";
        public static Bitmap getbitmap(String imageUri) {
            Log.v(TAG, "getbitmap:" + imageUri);
            // 显示网络上的图片
            Bitmap bitmap = null;
            try {
                URL myFileUrl = new URL(imageUri);
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();

                Log.v(TAG, "image download finished." + imageUri);
            } catch (IOException e) {
                e.printStackTrace();
                Log.v(TAG, "getbitmap bmp fail---");
                return null;
            }
            return bitmap;
        }
    }

    /**
     *
     */

    /**
     *
     */
    //显示获取到的头像和昵称
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {//获取昵称
                //tvNickName.setText((CharSequence) msg.obj);
            } else if (msg.what == 1) {//获取头像
                //headerLogo.setImageBitmap((Bitmap) msg.obj);
                circleImageView_user.setImageBitmap((Bitmap) msg.obj);
                textView_login.setText("退出登录");
            }
        }
    };
    /**
     *
     */

    /**
     *
     */

    /**
     *
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("宁顺文的新闻客户端");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://www.baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("快来加入我们！");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://www.baidu.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("评论");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("NewsDay");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://www.baidu.com");

// 启动分享GUI
        oks.show(this);
    }

    /**
     *
     */


}
