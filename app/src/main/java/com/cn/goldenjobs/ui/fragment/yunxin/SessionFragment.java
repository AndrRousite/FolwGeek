package com.cn.goldenjobs.ui.fragment.yunxin;

import android.view.View;
import android.widget.AdapterView;

import com.cn.goldenjobs.bean.SessionBean;
import com.iyiyo.mvc.adapter.ListBaseAdapter;
import com.iyiyo.mvc.bean.Entity;
import com.iyiyo.mvc.ui.fragment.BaseListFragment;

/**
 * 会话列表
 * Created by liu-feng on 2016/8/1.
 * 邮箱:w710989327@foxmail.com
 */
public class SessionFragment extends YunXinListFragment<SessionBean> {

    @Override
    public void initView(View view) {
        super.initView(view);
    }

    @Override
    protected ListBaseAdapter<SessionBean> getListAdapter() {
        return null;
    }

    @Override
    protected void sendRequestData() {
        // TODO
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
    }

}
