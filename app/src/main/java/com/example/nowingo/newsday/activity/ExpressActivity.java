package com.example.nowingo.newsday.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.adapter.ExpressAdapter;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.entity.Express;
import com.example.nowingo.newsday.http.HttpEntity;
import com.example.nowingo.newsday.manager.ToolbarManager;
import com.example.nowingo.newsday.quicksidebardemo.ExpressComActivity;
import com.example.nowingo.newsday.quicksidebardemo.model.ExpressCom;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NowINGo on 2017/1/11.
 */
public class ExpressActivity extends BaseActivity {
    View view;
    Button button_ok,button_com;
    EditText editText;
    ListView listView;
    ArrayList<Express.ShowapiResBodyBean.DataBean> arrayList;
    ExpressAdapter expressAdapter;
    MyReciver myReciver;
    String com;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_express);
    }

    @Override
    public void inidata() {
        com = null;
        //在Oncreate里面注册
        myReciver=new MyReciver();
        //绑定意图过滤器
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("setexpress");
        //执行注册
        registerReceiver(myReciver,intentFilter);
        expressAdapter = new ExpressAdapter(this);
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_express_toolbar);
        button_ok = (Button) findViewById(R.id.activity_express_ok_btn);
        button_com = (Button) findViewById(R.id.activity_express_com_btn);
        listView = (ListView) findViewById(R.id.activity_express_list);
        editText = (EditText) findViewById(R.id.activity_express_et);
    }

    @Override
    public void setview() {
        ToolbarManager.setTitle(view,this,"查询快递");
        listView.setAdapter(expressAdapter);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (com == null){
                    Toast.makeText(ExpressActivity.this,"请选择公司",Toast.LENGTH_SHORT).show();
                }else {
                    if (editText.getText().toString().trim().equals("")){
                        Toast.makeText(ExpressActivity.this,"请选择公司",Toast.LENGTH_SHORT).show();
                    }else {
                        Http(editText.getText().toString().trim(), com);
                    }
                }
            }
        });
        button_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExpressComActivity.class);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReciver);
    }

    private void Http(final String number, final String com){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://route.showapi.com/64-19", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Express express = new Gson().fromJson(s,Express.class);
                Log.i("msg",s);
                Message message = new Message();
                message.what = 0;
                message.obj = express;
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(ExpressActivity.this,"对不起，没有查询到相关信息。",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("showapi_appid", HttpEntity.APP_ID);
                map.put("com",com);
                map.put("nu",number);
                map.put("showapi_sign", HttpEntity.HTTP_KEY);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    arrayList = new ArrayList<>();
                    Express express = (Express) msg.obj;
                    arrayList = (ArrayList<Express.ShowapiResBodyBean.DataBean>) express.getShowapi_res_body().getData();
                    if (arrayList == null){
                        Toast.makeText(ExpressActivity.this,"对不起，没有查询到相关信息。",Toast.LENGTH_SHORT).show();
                    }else {
                        if (arrayList.size() == 0) {
                            Toast.makeText(ExpressActivity.this,"对不起，没有查询到相关信息。",Toast.LENGTH_SHORT).show();
                        } else {
                            expressAdapter.setList(arrayList);
                            expressAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }
        }
    };

    //自定义的广播接收器
    class MyReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //是你响应了这个广播 要执行操作
            String Action=intent.getAction();//获得Action
            if(Action.equals("setexpress")){//判断Action
                Bundle bundle = intent.getExtras();
                ExpressCom expressCom = (ExpressCom) bundle.getSerializable("express");
                button_com.setText(expressCom.getExpName());
                com = expressCom.getExpSpellName();
            }
        }
    }

}
