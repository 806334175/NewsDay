package com.example.nowingo.newsday.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.manager.ShareManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class LoadingActivity extends BaseActivity {
    ImageView imageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_loading);
    }

    @Override
    public void inidata() {
        ShareManager.saveIsFirst(this,true);
    }

    @Override
    public void iniview() {
        imageView = (ImageView) findViewById(R.id.activity_loading);
    }

    @Override
    public void setview() {
        Glide.with(this).load(R.mipmap.first_loading).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
                timer.cancel();
            }
        };
        timer.schedule(timerTask,1000);
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    startActivity(MainActivity.class);
                    overridePendingTransition(R.anim.anim_fori_0to1,R.anim.anim_fori_1to0);
                    finish();
                break;
            }
        }
    };
}
