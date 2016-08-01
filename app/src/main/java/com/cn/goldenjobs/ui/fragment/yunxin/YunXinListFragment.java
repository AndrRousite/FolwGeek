package com.cn.goldenjobs.ui.fragment.yunxin;

import com.iyiyo.mvc.adapter.ListBaseAdapter;
import com.iyiyo.mvc.bean.Entity;
import com.iyiyo.mvc.interf.OnTabReselectListener;
import com.iyiyo.mvc.ui.fragment.BaseListFragment;

/**
 * Created by liu-feng on 2016/8/1.
 * 邮箱:w710989327@foxmail.com
 */
public abstract class YunXinListFragment<T extends Entity> extends BaseListFragment implements OnTabReselectListener {

    @Override
    public void onTabReselect() {
        mListView.setSelection(0);
        mSwipeRefreshLayout.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(int v) {

    }
}
