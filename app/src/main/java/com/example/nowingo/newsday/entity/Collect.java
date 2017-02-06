package com.example.nowingo.newsday.entity;

/**
 * Created by NowINGo on 2017/1/5.
 */
public class Collect {
    private String msg;
    private String from;

    public Collect(String msg, String from) {
        this.msg = msg;
        this.from = from;
    }



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
