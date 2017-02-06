package com.example.nowingo.newsday.entity;

/**
 *
 */
public class TypeBean {

    /**
     * wid : 00
     * weather : 晴
     */

    private String wid;
    private String weather;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public TypeBean(String wid, String weather) {
        this.wid = wid;
        this.weather = weather;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "wid='" + wid + '\'' +
                ", weather='" + weather + '\'' +
                '}';
    }
}
