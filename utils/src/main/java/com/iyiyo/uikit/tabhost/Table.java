package com.iyiyo.uikit.tabhost;

/**
 * 地步导航条的Item
 * Created by liu-feng on 2016/7/13.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public enum Table {

//	NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
//			NewsViewPagerFragment.class),

//    NEWS(0, R.string.main_tab_name_news, R.drawable.tab_icon_new,
//            GeneralViewPagerFragment.class),
//
//    TWEET(1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
//            TweetsViewPagerFragment.class),
//
//    QUICK(2, R.string.main_tab_name_quick, R.drawable.tab_icon_new,
//            null),
//
//    EXPLORE(3, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
//            ExploreFragment.class),
//
//    ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
//            MyInformationFragment.class);
    ;  // TODO

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
