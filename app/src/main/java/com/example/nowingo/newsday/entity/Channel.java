package com.example.nowingo.newsday.entity;

import java.io.Serializable;

/**
 * Created by NowINGo on 2017/1/3.
 */
public class Channel implements Serializable{
    private String channelid;
    private String name;
    private String choose;

    public Channel(String channelid, String name, String choose) {
        this.channelid = channelid;
        this.name = name;
        this.choose = choose;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }
}
