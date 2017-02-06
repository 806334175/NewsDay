package com.example.nowingo.newsday.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.manager.ToolbarManager;


/**
 * Created by NowINGo on 2017/1/6.
 */
public class AboutUsActivity extends BaseActivity {
    TextView textView_version;
    View view;
    String version;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    public void inidata() {
        PackageInfo p = null;
        try {
            p = this.getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = p.versionName;
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_aboutus_toolbar);
        textView_version = (TextView) findViewById(R.id.activity_aboutus_version);
    }

    @Override
    public void setview() {
        ToolbarManager.setTitle(view,this,"关于我们");
        textView_version.setText("版本："+version);
    }
}
