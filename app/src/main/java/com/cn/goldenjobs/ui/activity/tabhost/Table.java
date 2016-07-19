package com.cn.goldenjobs.ui.activity.tabhost;

import com.cn.goldenjobs.R;
import com.cn.goldenjobs.ui.fragment.ExampleFragment;

/**
 * 地步导航条的Item
 * Created by liu-feng on 2016/7/13.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public enum Table {

    MODULE(0, R.string.tab_name_module, R.drawable.selector_module,
            ExampleFragment.class),
    NEWS(1, R.string.tab_name_news, R.drawable.selector_news,
            ExampleFragment.class),
    CENTER(2, R.string.tab_name_center, R.drawable.selector_center,
            null),
    MESSAGE(3, R.string.tab_name_message, R.drawable.selector_message,
            ExampleFragment.class),
    MINE(4, R.string.tab_name_mine, R.drawable.selector_mine,
            ExampleFragment.class);

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
