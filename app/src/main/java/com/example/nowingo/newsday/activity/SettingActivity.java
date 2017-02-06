package com.example.nowingo.newsday.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.HistoryDbExpress;
import com.example.nowingo.newsday.manager.ShareManager;
import com.example.nowingo.newsday.manager.ToolbarManager;
import com.zhy.changeskin.SkinManager;


/**
 * Created by NowINGo on 2017/1/6.
 */
public class SettingActivity extends BaseActivity {
    View view;
    CheckBox checkBox_img;
    TextView textView_clean;
    HistoryDbExpress historyDbExpress;
    LinearLayout linearLayout_bg_1;
    LinearLayout linearLayout_bg_2;
    LinearLayout linearLayout_bg_3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void inidata() {
        historyDbExpress = new HistoryDbExpress(this);
    }

    @Override
    public void iniview() {
        view = findViewById(R.id.activity_setting_toolbar);
        checkBox_img = (CheckBox) findViewById(R.id.activity_setting_img_cb);
        textView_clean = (TextView) findViewById(R.id.activity_setting_cleanhistory_tv);
        linearLayout_bg_1 = (LinearLayout) findViewById(R.id.activity_setting_bg_1);
        linearLayout_bg_2 = (LinearLayout) findViewById(R.id.activity_setting_bg_2);
        linearLayout_bg_3 = (LinearLayout) findViewById(R.id.activity_setting_bg_3);
    }

    @Override
    public void setview() {
        ToolbarManager.setTitle(view,this,"设置");
        checkBox_img.setChecked(ShareManager.loadHaveImg(this));
        checkBox_img.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShareManager.saveHaveImg(SettingActivity.this,isChecked);
            }
        });
        textView_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        linearLayout_bg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().changeSkin("red");
                Toast.makeText(SettingActivity.this,"换肤成功",Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout_bg_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().changeSkin("green");
                Toast.makeText(SettingActivity.this,"换肤成功",Toast.LENGTH_SHORT).show();
            }
        });

        linearLayout_bg_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.zhy.changeskin.SkinManager.getInstance().removeAnySkin();
                Toast.makeText(SettingActivity.this,"换肤成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否删除");
        builder.setMessage("请问您确定删除历史记录吗？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                historyDbExpress.deleteall();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
}
