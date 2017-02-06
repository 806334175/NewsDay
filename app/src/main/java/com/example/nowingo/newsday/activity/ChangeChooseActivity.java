package com.example.nowingo.newsday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.adapter.ChangeChooseAdapter;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.ChannelDbExpress;
import com.example.nowingo.newsday.entity.Channel;
import com.example.nowingo.newsday.manager.ToolbarManager;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NowINGo on 2016/12/28.
 */
public class ChangeChooseActivity extends BaseActivity {
    //Toolbar toolbar;
    View view;
    RecyclerView recyclerView_choose;
    RecyclerView recyclerView_unchoose;
    ArrayList<Channel> arrayList_choose;
    ArrayList<Channel> arrayList_unchoose;
    ChangeChooseAdapter adapter_choose;
    ChangeChooseAdapter adapter_unchoose;
    ChannelDbExpress cde;
    boolean check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_changechoose);
    }

    @Override
    public void inidata() {
        arrayList_choose = new ArrayList<>();
        arrayList_unchoose = new ArrayList<>();
        adapter_unchoose = new ChangeChooseAdapter(this);
        cde = new ChannelDbExpress(this);
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_changechoose_toolbar);
        //toolbar = (Toolbar) findViewById(R.id.activity_changechoose_toolbar);
        recyclerView_choose = (RecyclerView) findViewById(R.id.activity_changechoose_choose_rlv);
        recyclerView_unchoose = (RecyclerView) findViewById(R.id.activity_changechoose_unchoose_rlv);
    }

    @Override
    public void setview() {
        ToolbarManager.setTitle(view,this,"标签");
//        toolbar.setTitle("NewsDay");
//        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
//        recyclerView_choose.setLayoutManager(new GridLayoutManager(this,8));
        recyclerView_choose.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        adapter_choose = new ChangeChooseAdapter(this);
        recyclerView_choose.setAdapter(adapter_choose);
        recyclerView_unchoose.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        recyclerView_unchoose.setAdapter(adapter_unchoose);
        arrayList_choose = cde.getNameByChoose("true");
        adapter_choose.setArrayList(arrayList_choose);
        adapter_choose.notifyDataSetChanged();
        arrayList_unchoose = cde.getNameByChoose("false");
        adapter_unchoose.setArrayList(arrayList_unchoose);
        adapter_unchoose.notifyDataSetChanged();

        adapter_choose.setOnClick(new ChangeChooseAdapter.OnClick() {
            @Override
            public void setItemClick(View view, int postion) {
                if (check){

                }else {
                    check = true;
                    adapter_unchoose.addData(arrayList_choose.get(postion));
                    arrayList_choose.get(postion).setChoose("false");
                    cde.changeChooseByName(arrayList_choose.get(postion).getName(), arrayList_choose.get(postion));
                    adapter_choose.removeData(postion);
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            check = false;
                        }
                    };
                    timer.schedule(timerTask,500);
                }
            }
        });

        adapter_unchoose.setOnClick(new ChangeChooseAdapter.OnClick() {
            @Override
            public void setItemClick(View view, int postion) {
                if (check){

                }else {
                    check = true;
                    adapter_choose.addData(arrayList_unchoose.get(postion));
                    arrayList_unchoose.get(postion).setChoose("true");
                    cde.changeChooseByName(arrayList_unchoose.get(postion).getName(), arrayList_unchoose.get(postion));
                    adapter_unchoose.removeData(postion);
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            check = false;
                        }
                    };
                    timer.schedule(timerTask,500);
                }
            }
        });

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
}
