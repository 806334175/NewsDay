package com.example.nowingo.newsday.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.adapter.CollectAdapter;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.CollectDbExpress;
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.entity.News2;
import com.example.nowingo.newsday.manager.ToolbarManager;
import com.google.gson.Gson;

/**
 * Created by NowINGo on 2017/1/6.
 */
public class CollectActivity extends BaseActivity {
    RecyclerView recyclerView;
    CollectAdapter collectAdapter;
    CollectDbExpress collectDbExpress;
    View view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_collect);
    }

    @Override
    public void inidata() {
        collectDbExpress = new CollectDbExpress(this);
        collectAdapter = new CollectAdapter(this);
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_collect_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.activity_collect_rv);
    }
    @Override
    public void setview() {
        ToolbarManager.setTitle(view,this,"收藏");
        recyclerView.setAdapter(collectAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        collectAdapter.setArrayList(collectDbExpress.findall());
        collectAdapter.notifyDataSetChanged();
        collectAdapter.setOnItemClickLitener(new CollectAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int postion) {
                if ( collectAdapter.getArrayList().get(postion).getFrom().equals("1")) {
                    Intent intent = new Intent(CollectActivity.this, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", 1);
                    News2.ResultEntity.DataEntity dataEntity = new Gson().fromJson(collectAdapter.getArrayList().get(postion).getMsg(), News2.ResultEntity.DataEntity.class);
                    bundle.putSerializable("nsw", dataEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if (collectAdapter.getArrayList().get(postion).getFrom().equals("2")){
                    Intent intent = new Intent(CollectActivity.this, WebViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", 2);
                    News.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = new Gson().fromJson(collectAdapter.getArrayList().get(postion).getMsg(), News.ShowapiResBodyBean.PagebeanBean.ContentlistBean.class);
                    bundle.putSerializable("nsw", contentlistBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
