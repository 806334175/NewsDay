package com.example.nowingo.newsday.manager;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.nowingo.newsday.R;


/**
 * Created by NowINGo on 2017/1/6.
 */
public class ToolbarManager {

    public static void setTitle(View view, final AppCompatActivity activity, String title){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.top_toolbar);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.parseColor("#ffffff")); //设置标题颜色
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_return);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
