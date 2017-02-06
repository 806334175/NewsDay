package com.example.nowingo.newsday.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.BaseActivity;
import com.example.nowingo.newsday.db.CollectDbExpress;
import com.example.nowingo.newsday.entity.Collect;
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.entity.News2;
import com.google.gson.Gson;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class WebViewActivity extends BaseActivity {
    WebView webView;
    ProgressBar progressBar;
    TextView textView_back,textView_forward;
    FloatingActionButton fab;
    CollectDbExpress collectDbExpress;
    CoordinatorLayout coordinatorLayout;

    String url;
    String msg;
    int from;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_webview);
    }

    @Override
    public void inidata() {
        Bundle bundle = this.getIntent().getExtras();
        from = bundle.getInt("data");
        switch (from){
            case 1:
                News2.ResultEntity.DataEntity dataEntity = (News2.ResultEntity.DataEntity) bundle.getSerializable("nsw");
                msg = new Gson().toJson(dataEntity);
                url = dataEntity.getUrl();
                break;
            case 2:
                News.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = (News.ShowapiResBodyBean.PagebeanBean.ContentlistBean) bundle.getSerializable("nsw");
                msg = new Gson().toJson(contentlistBean);
                url = contentlistBean.getLink();
                break;
        }

    }

    @Override
    public void iniview() {
        webView = (WebView) findViewById(R.id.activity_webview);
        progressBar = (ProgressBar) findViewById(R.id.activity_webview_pro);
        textView_back = (TextView) findViewById(R.id.activity_webview_back);
        textView_forward = (TextView) findViewById(R.id.activity_webview_forward);
        fab = (FloatingActionButton) findViewById(R.id.activity_webview_collect);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_webview_snackbar);
        collectDbExpress = new CollectDbExpress(this);
    }

    @Override
    public void setview() {
        Log.i("msg",url);
        webView.loadUrl(url);
//        webView.loadDataWithBaseURL("file:///android_asset/", pageContent, "text/html", "UTF-8", null);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                if (newProgress == 100) {
                    // 网页加载完成
                    progressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    progressBar.setProgress(newProgress);
                }

            }
        });

        //使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //不使用缓存
        //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        textView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();

            }
        });
        textView_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goForward();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (collectDbExpress.findone(msg)){
            fab.setImageResource(R.drawable.star_selected);
        }else {
            fab.setImageResource(R.drawable.star_defult);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collectDbExpress.findone(msg)){
                    collectDbExpress.delete(msg);
                    fab.setImageResource(R.drawable.star_defult);
                    Snackbar.make(coordinatorLayout,"取消收藏成功", Snackbar.LENGTH_SHORT).show();
                }else {
                    Collect collect = new Collect(msg,from+"");
                    collectDbExpress.add(collect);
                    fab.setImageResource(R.drawable.star_selected);
                    Snackbar.make(coordinatorLayout,"收藏成功", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
