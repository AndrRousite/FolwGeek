package com.cn.goldenjobs.bean;

import com.iyiyo.mvc.bean.Entity;

/**
 * Created by liu-feng on 2016/7/19.
 * 邮箱:w710989327@foxmail.com
 */
public class City extends Entity {

    /**
     * cityName : 鞍山
     * firstLetter : A
     */

    private String cityName;
    private String firstLetter;

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
