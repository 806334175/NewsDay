package com.example.nowingo.newsday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.activity.WebViewActivity;
import com.example.nowingo.newsday.adapter.HistoryAdapter;
import com.example.nowingo.newsday.adapter.NewsFindAdapter;
import com.example.nowingo.newsday.db.HistoryDbExpress;
import com.example.nowingo.newsday.entity.History;
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.http.HttpEntity;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NowINGo on 2016/12/21.
 */
public class FindFragment extends Fragment {
    RelativeLayout relativeLayout_refresh,relativeLayout_loading;
    ImageView imageView_loading;
    ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> arrayList;
    NewsFindAdapter newsFindAdapter;
    EditText editText;
    ImageView imageView_clean;
    RecyclerView recyclerView;
    LinearLayout linearLayout_history;
    ListView listView;
    HistoryAdapter historyAdapter;
    HistoryDbExpress historyDbExpress;
    ArrayList<History> historyArrayList;
    ArrayList<History> allhistoryArrayList;
    String string;
    boolean flag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        relativeLayout_refresh = (RelativeLayout) view.findViewById(R.id.fragment_find_loading_refresh);
        imageView_loading = (ImageView) view.findViewById(R.id.fragment_find_loading_iv);
        Glide.with(getContext()).load(R.mipmap.my_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView_loading);
        relativeLayout_loading = (RelativeLayout) view.findViewById(R.id.fragment_find_loading_rl);
        editText = (EditText) view.findViewById(R.id.fragment_find_newsname);
        imageView_clean = (ImageView) view.findViewById(R.id.fragment_find_clean_et);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_find_rv);
        linearLayout_history = (LinearLayout) view.findViewById(R.id.fragment_find_history_ll);
        listView = (ListView) view.findViewById(R.id.fragment_find_history_lv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsFindAdapter = new NewsFindAdapter(getContext());
        recyclerView.setAdapter(newsFindAdapter);
        historyAdapter = new HistoryAdapter(getContext());
        listView.setAdapter(historyAdapter);
        historyDbExpress = new HistoryDbExpress(getContext());
        relativeLayout_loading.setVisibility(View.GONE);
        relativeLayout_refresh.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editText.setText(historyAdapter.getArrayList().get(position).getName());
                linearLayout_history.setVisibility(View.GONE);
                relativeLayout_loading.setVisibility(View.VISIBLE);
                relativeLayout_refresh.setVisibility(View.GONE);
                if (editText.getText().toString().trim().equals("")){

                }else {
                    if (flag){

                    }else {
                        string = editText.getText().toString().trim();
                        flag = true;
                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                Http(editText.getText().toString());
                            }
                        };
                        timer.schedule(timerTask,500);
                    }
                }
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyArrayList = new ArrayList<>();
                allhistoryArrayList = new ArrayList<>();

                allhistoryArrayList = historyDbExpress.findall();
                if (allhistoryArrayList.size()<10){
                    for (int i = 0,n = allhistoryArrayList.size()-1; i <=n ; n--) {
                        historyArrayList.add(allhistoryArrayList.get(n));
                    }
                }else {
                    for (int i = allhistoryArrayList.size()-1, n = allhistoryArrayList.size() - 10; n <= i; i--) {
                        historyArrayList.add(allhistoryArrayList.get(i));
                    }
                }
                historyAdapter.setArrayList(historyArrayList);
                historyAdapter.notifyDataSetChanged();
                linearLayout_history.setVisibility(View.VISIBLE);
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER){
                    relativeLayout_loading.setVisibility(View.VISIBLE);
                    relativeLayout_refresh.setVisibility(View.GONE);
                    if (editText.getText().toString().trim().equals("")){

                    }else {
                        if (flag){

                        }else {
                            string = editText.getText().toString().trim();
                            flag = true;
                            Timer timer = new Timer();
                            TimerTask timerTask = new TimerTask() {
                                @Override
                                public void run() {

                                    Http(editText.getText().toString());
                                }
                            };
                            timer.schedule(timerTask,500);
                        }
                    }
                }
                return false;
            }

        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("")){
                    imageView_clean.setVisibility(View.GONE);
                }else {
                    imageView_clean.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {

            }
        });

        imageView_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        newsFindAdapter.setOnItemClickLitener(new NewsFindAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("data",2);
                bundle.putSerializable("nsw",arrayList.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void Http(final String name) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://route.showapi.com/109-35", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("msg","333");
                linearLayout_history.setVisibility(View.GONE);
                News news = new Gson().fromJson(s,News.class);
                Message message = new Message();
                message.what = 0;
                message.obj = news;
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                flag = false;
                handler.sendEmptyMessage(1);
                relativeLayout_refresh.setVisibility(View.VISIBLE);
                relativeLayout_loading.setVisibility(View.GONE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("showapi_appid", HttpEntity.APP_ID);
                map.put("title",name);
                map.put("showapi_sign", HttpEntity.HTTP_KEY);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        requestQueue.start();
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    save();
                    arrayList = new ArrayList<>();
                    News news = (News) msg.obj;
                    if (news == null){
                    }
                    arrayList = (ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) news.getShowapi_res_body().getPagebean().getContentlist();
                    if (arrayList.size() == 0){
                        Toast.makeText(getContext(),"没有数据",Toast.LENGTH_SHORT).show();
                        relativeLayout_refresh.setVisibility(View.GONE);
                        relativeLayout_loading.setVisibility(View.GONE);
                        flag = false;
                    }else {
                        newsFindAdapter.setArrayList(arrayList);
                        newsFindAdapter.notifyDataSetChanged();
                        flag = false;
                        relativeLayout_loading.setVisibility(View.GONE);
                        relativeLayout_refresh.setVisibility(View.GONE);
                    }
                    break;
                case 1:

                    break;
            }
        }
    };

    public void save(){
        String name = string;
        String date = new SimpleDateFormat("yyyy-MM-dd HH:ss:SS").format(new Date());
        if (historyDbExpress.ishave(name)){
            historyDbExpress.delete(name);
        }
        History history = new History(name,date);
        historyDbExpress.add(history);
    }
}
