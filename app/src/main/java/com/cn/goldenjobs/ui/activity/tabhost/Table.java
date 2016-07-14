package com.cn.goldenjobs.ui.activity.tabhost;

import com.iyiyo.mvp.ui.fragment.BaseFragment;
import com.iyiyo.utils.R;

/**
 * 地步导航条的Item
 * Created by liu-feng on 2016/7/13.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public enum Table {

    MODULE(0, R.string.tab_name_module, R.drawable.iconfont_webzhuye,
            BaseFragment.class),
    NEWS(0, R.string.tab_name_news, R.drawable.iconfont_zhaomingdianzi,
            BaseFragment.class),
    MESSAGE(0, R.string.tab_name_module, R.drawable.iconfont_qicheyongpin,
            BaseFragment.class),
    MINE(0, R.string.tab_name_module, R.drawable.iconfont_fuwushichang,
            BaseFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private Table(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
