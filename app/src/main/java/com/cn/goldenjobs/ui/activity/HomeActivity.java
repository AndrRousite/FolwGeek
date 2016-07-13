package com.cn.goldenjobs.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;

import com.cn.goldenjobs.R;
import com.iyiyo.mvp.ui.activity.BaseActivity;
import com.iyiyo.uikit.BottomFragmentTabHost;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 功能界面，显示4个TableBar
 * 1. 瓷片布局的功能页面，类似于支付宝的首页
 * 2. 发现页面（资讯详情，嵌套FragmentViewPager）
 * 3. IM页面（实现IM功能：用云信（网易））
 * 4. 我的界面（可以借鉴支付宝（延伸多个领域的界面））
 * Created by liu-feng on 2016/7/13.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public class HomeActivity extends BaseActivity implements
        TabHost.OnTabChangeListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.layout_frame)
    FrameLayout layoutFrame;
    @Bind(android.R.id.tabhost)
    BottomFragmentTabHost tabhost;
    @Bind(R.id.table_iv)
    ImageView tableIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * *****************************************
     */
    private void initView() {
        toolbar.setTitle("");
        toolbar.setSubtitle("Home");
        toolbar.setLogo(R.mipmap.ic_home);
        setSupportActionBar(toolbar);
        tabhost.setup(this, getSupportFragmentManager(), R.id.layout_frame);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            tabhost.getTabWidget().setShowDividers(0);
        }
        tabhost.setCurrentTab(0);
        tabhost.setOnTabChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_android: //

                break;
            case R.id.menu_apple:  //

                break;
            case R.id.menu_windows:  //

                break;
            case R.id.menu_more:  //

                break;
        }

        return true;
    }

    @Override
    public void onTabChanged(String tabId) {

    }
}
