package com.cn.goldenjobs.bean;

import com.iyiyo.mvp.domain.Entity;

/**
 * 笑话大全
 * Created by liu-feng on 2016/7/16.
 * 邮箱:w710989327@foxmail.com
 */
public class ImageUrlList extends Entity{
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    private String title,url,type,updatetime;
}
