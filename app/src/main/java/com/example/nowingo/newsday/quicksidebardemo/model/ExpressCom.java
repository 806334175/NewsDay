package com.example.nowingo.newsday.quicksidebardemo.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/1/12.
 */
public class ExpressCom implements Serializable{
    private String expName;
    private String expSpellName;
    private String firstLetter;

    public ExpressCom(String expName, String expSpellName, String firstLetter) {
        this.expName = expName;
        this.expSpellName = expSpellName;
        this.firstLetter = firstLetter;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getExpSpellName() {
        return expSpellName;
    }

    public void setExpSpellName(String expSpellName) {
        this.expSpellName = expSpellName;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
