package com.example.nowingo.newsday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.example.nowingo.newsday.adapter.NewsAdapter;
import com.example.nowingo.newsday.entity.Channel;
import com.example.nowingo.newsday.entity.News2;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NowINGo on 2016/12/21.
 */
public class NewsViewPagerFragment extends Fragment {
    ImageView imageView_loading;

    View view;
    SwipeRefreshLayout refreshLayout;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout_refresh;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    ArrayList<News2.ResultEntity.DataEntity> arrayList;
    ArrayList<News2.ResultEntity.DataEntity> list;
    LinearLayoutManager linearLayoutManager;

    //判断是否上滑
    boolean isScroll;
    //判断是否正在加载
    boolean isLoading;
    //判断是否还有数据
    boolean noMore;
    //加载数据的个数
    int number;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news_viewpager,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView_loading = (ImageView) view.findViewById(R.id.fragment_news_viewpager_loading);
        Glide.with(getContext()).load(R.mipmap.my_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView_loading);
        relativeLayout_refresh = (RelativeLayout) view.findViewById(R.id.fragment_news_viewpager_refresh);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.fragment_news_viewpager_pro);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_news_viewpager_srl);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_news_viewpager_rlv);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//ListView布局
        newsAdapter = new NewsAdapter(getContext());
        //获得布局管理器
        linearLayoutManager = new LinearLayoutManager(getContext());
        number = 10;
        //用bundle获取Fragment传过来的值
        Bundle bundle = getArguments();
        final Channel channel = (Channel) bundle.getSerializable("A");
        //设置刷新控件的颜色
        refreshLayout.setColorSchemeResources(R.color.colorforsrlred,R.color.colorforsrlblue,R.color.colorforsrlgreed);
        //给recyclerview设置适配器
        recyclerView.setAdapter(newsAdapter);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //刷新监听
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               //设置刷新控件可见
               refreshLayout.setRefreshing(true);
               //执行刷新操作
               Http(channel.getChannelid());
           }
       });
        //recyclerview设置滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断是否正在刷新
                if (refreshLayout.isRefreshing()){

                }else {
                    //判断是否到了底部
                    if (linearLayoutManager.findLastVisibleItemPosition() == newsAdapter.getArrayList().size()) {

                        //2.代表惯性移动距离
                        //1.代表手指移动
                        //0.代表停止
                        switch (newState) {
                            case 0:
                                if (isScroll) {
                                    newsAdapter.setFootState(NewsAdapter.DATA_LOADING);
                                    loadMore();
                                }
                                break;
                            case 1:
                                isScroll = false;
                                break;
                            case 2:
                                isScroll = true;
                                break;
                        }
                    }
                }
            }
        });

        //设置点击刷新页面
        relativeLayout_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置刷新等待页面可见
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout_refresh.setVisibility(View.GONE);
                Http(channel.getChannelid());
            }
        });
        //加载数据
        Http(channel.getChannelid());
    }


    @Override
    public void onResume() {
        super.onResume();
        newsAdapter.setOnItemClickLitener(new NewsAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("data",1);
                bundle.putSerializable("nsw",arrayList.get(postion));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    public void Http(final String channelid){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://v.juhe.cn/toutiao/index", new Response.Listener<String>() {
            @Override
            //连接成功
            public void onResponse(String s) {
                //Gson框架解析数据
                News2 news2 = new Gson().fromJson(s,News2.class);
                Message message = new Message();
                message.what = 0;
                message.obj = news2;
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            //链接失败
            public void onErrorResponse(VolleyError volleyError) {
                //设置刷新控件不可见
                refreshLayout.setRefreshing(false);
                //设置刷新等待页面不可见
                relativeLayout.setVisibility(View.GONE);
                //设置点击刷新
                relativeLayout_refresh.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("type",channelid);
                map.put("key","237658ec4e5e6854e4ef226aff7c7e85");
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
                    number = 10;
                    arrayList = new ArrayList<>();
                    list = new ArrayList<>();
                    arrayList.clear();
                    list.clear();
                    noMore =  false;
                    News2 news2 = (News2) msg.obj;
                    refreshLayout.setRefreshing(false);
                    arrayList = (ArrayList<News2.ResultEntity.DataEntity>) news2.getResult().getData();
                    if (arrayList.size() == 0){
                        relativeLayout.setVisibility(View.GONE);
                    }else {
                        for (int i = 0; i < number; i++) {
                            list.add(arrayList.get(i));
                        }
                        relativeLayout.setVisibility(View.GONE);
                        relativeLayout_refresh.setVisibility(View.GONE);
                        newsAdapter.setArrayList(list);
                        newsAdapter.notifyDataSetChanged();
                        number += 10;
                    }
                    break;
                case 1:
                    isLoading = false;
                        if (number >= arrayList.size()) {
                            noMore = true;
                            number = arrayList.size();
                        }

                        list = newsAdapter.getArrayList();
                        for (int i = list.size(); i < number; i++) {
                            list.add(arrayList.get(i));
                        }
                        newsAdapter.setArrayList(list);
                        if (noMore) {
                            newsAdapter.setFootState(NewsAdapter.DATA_NOMORE);
                        } else {
                            newsAdapter.setFootState(NewsAdapter.DATA_FINISH);
                            number += 10;//再增加一次
                        }
                        newsAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


    void loadMore(){
        if(isLoading){
            return ;
        }
        newsAdapter.setFootState(NewsAdapter.DATA_LOADING);
        newsAdapter.reFresh(newsAdapter.getArrayList().size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                isLoading=true;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    handler.sendEmptyMessage(1);//加载更多完成
                }
            }
        }).start();
    }
}
