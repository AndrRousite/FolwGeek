package com.cn.goldenjobs.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.cn.goldenjobs.R;
import com.cn.goldenjobs.bean.ContactBean;
import com.cn.goldenjobs.bean.LiveBean;
import com.cn.goldenjobs.bean.SessionBean;
import com.cn.goldenjobs.ui.fragment.yunxin.ContactFragment;
import com.cn.goldenjobs.ui.fragment.yunxin.LiveFragment;
import com.cn.goldenjobs.ui.fragment.yunxin.SessionFragment;
import com.cn.goldenjobs.ui.fragment.yunxin.YunXinListFragment;
import com.iyiyo.mvc.adapter.viewpager.ViewPageFragmentAdapter;
import com.iyiyo.mvc.interf.OnTabReselectListener;
import com.iyiyo.mvc.ui.fragment.BaseListFragment;
import com.iyiyo.mvc.ui.fragment.BaseViewPagerFragment;

/**
 * Session界面
 * Created by liu-feng on 2016/7/30.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public class MessageViewPageFragment extends BaseViewPagerFragment implements
        OnTabReselectListener {


    @Override
    protected void onSetupTabAdapter(ViewPageFragmentAdapter adapter) {
        String[] title = getResources().getStringArray(
                R.array.message_array);

        adapter.addTab(title[0], "session", SessionFragment.class,
                getBundle(SessionBean.CATALOG_SESSION));
        adapter.addTab(title[1], "contact", ContactFragment.class,
                getBundle(ContactBean.CATALOG_CONTACT));
        adapter.addTab(title[2], "live", LiveFragment.class,
                getBundle(LiveBean.CATALOG_LIVE));
    }

    private Bundle getBundle(int newType) {
        Bundle bundle = new Bundle();
        bundle.putInt(BaseListFragment.BUNDLE_KEY_CATALOG, newType);
        return bundle;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(int v) {

    }

    @Override
    public void onTabReselect() {
        Fragment fragment = mTabsAdapter.getItem(mViewPager.getCurrentItem());
        if (fragment != null && fragment instanceof YunXinListFragment) {
            ((YunXinListFragment) fragment).onTabReselect();
        }
    }

    /**
     * ViewPager缺省情况下，只把当前页的前一页和后一页放在缓冲区中
     * setOffscreenPageLimit(2) 可以设置前后两页可以缓冲，不需要重新创建
     * 适用于Fragment较少的情况
     */
    @Override
    protected void setScreenPageLimit() {
        super.setScreenPageLimit();
        mViewPager.setOffscreenPageLimit(2);
    }
}
