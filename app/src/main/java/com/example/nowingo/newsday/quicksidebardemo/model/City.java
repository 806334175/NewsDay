package com.example.nowingo.newsday.quicksidebardemo.model;

/**
 * Created by Sai on 16/3/28.
 */
public class City {

    /**
     * cityName : 鞍山
     * firstLetter : A
     */

    private String cityName;
    private String firstLetter;

    public City(String cityName, String firstLetter) {
        this.cityName = cityName;
        this.firstLetter = firstLetter;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getCityName() {
        return cityName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }
}
