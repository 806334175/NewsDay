package com.example.nowingo.newsday.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import com.example.nowingo.newsday.adapter.NewsHottopicAdapter;
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.http.HttpEntity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by NowINGo on 2016/12/21.
 */
public class HotTopicFragment extends Fragment {
    RelativeLayout relativeLayout_refresh,relativeLayout_loading;
    ImageView imageView_loading;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    NewsHottopicAdapter newsHottopicAdapter;
    ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> arrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hottopic,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        relativeLayout_refresh = (RelativeLayout) view.findViewById(R.id.fragment_hottopic_loading_refresh);
        imageView_loading = (ImageView) view.findViewById(R.id.fragment_hottopic_loading_iv);
        Glide.with(getContext()).load(R.mipmap.my_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView_loading);
        relativeLayout_loading = (RelativeLayout) view.findViewById(R.id.fragment_hottopic_loading_rl);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_collect_srl);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_collect_rv);
        newsHottopicAdapter = new NewsHottopicAdapter(getContext());
        recyclerView.setAdapter(newsHottopicAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        swipeRefreshLayout.setColorSchemeResources(R.color.colorforsrlred,R.color.colorforsrlblue,R.color.colorforsrlgreed);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Http();
            }
        });
        Http();
        newsHottopicAdapter.setOnItemClickLitener(new NewsHottopicAdapter.OnItemClickLitener() {
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
        relativeLayout_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayout_loading.setVisibility(View.VISIBLE);
                relativeLayout_refresh.setVisibility(View.GONE);
                Http();
            }
        });
    }

    public void Http(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://route.showapi.com/109-35", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                News news = new Gson().fromJson(s,News.class);
                Message message = new Message();
                message.what = 0;
                message.obj = news;
                handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                swipeRefreshLayout.setRefreshing(false);
                relativeLayout_loading.setVisibility(View.GONE);
                relativeLayout_refresh.setVisibility(View.VISIBLE);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("showapi_appid", HttpEntity.APP_ID);
                map.put("channelName","游戏焦点");
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
                    arrayList = new ArrayList<>();
                    News news = (News) msg.obj;
                    arrayList = (ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean>) news.getShowapi_res_body().getPagebean().getContentlist();
                    if (arrayList.size() == 0){
                        relativeLayout_loading.setVisibility(View.GONE);
                    }else {
                        newsHottopicAdapter.setArrayList(arrayList);
                        newsHottopicAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                        relativeLayout_refresh.setVisibility(View.GONE);
                        relativeLayout_loading.setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    Log.i("msg","888");
                    break;
            }
        }
    };
}
