package com.cn.goldenjobs.bean;

import com.iyiyo.mvp.domain.Entity;

import java.util.List;

/**
 * 城市列表
 * Created by liu-feng on 2016/7/15.
 * 邮箱:w710989327@foxmail.com
 */
public class CityList extends Entity {
   int errNum;
    String retMsg;
    private RetData retData;

    public RetData getRetData() {
        return retData;
    }

    public void setRetData(RetData retData) {
        this.retData = retData;
    }

    public static class RetData extends Entity{
        private List<String> citylist;

        public List<String> getCitylist() {
            return citylist;
        }

        public void setCitylist(List<String> citylist) {
            this.citylist = citylist;
        }
    }
}
