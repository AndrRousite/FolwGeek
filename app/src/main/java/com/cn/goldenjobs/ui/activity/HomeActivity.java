package com.cn.goldenjobs.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cn.goldenjobs.R;
import com.cn.goldenjobs.config.JFWConstans;
import com.cn.goldenjobs.interf.OnTabReselectListener;
import com.cn.goldenjobs.ui.activity.tabhost.Table;
import com.iyiyo.mvp.ui.activity.BaseActivity;
import com.iyiyo.uikit.BottomFragmentTabHost;
import com.iyiyo.utils.SPUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        TabHost.OnTabChangeListener, View.OnTouchListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.layout_frame)
    FrameLayout layoutFrame;
    @Bind(android.R.id.tabhost)
    BottomFragmentTabHost tabhost;

    private long mBackPressedTime;

    @OnClick({R.id.table_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.table_iv:  // TabHost中间的按钮
                break;
        }
    }

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
        if (Build.VERSION.SDK_INT > 10) {
            tabhost.getTabWidget().setShowDividers(0);
        }
        initTabs();
        tabhost.setCurrentTab(0);
        tabhost.setOnTabChangedListener(this);
    }

    /**
     * 初始化Tab栏布局
     */
    private void initTabs() {
        Table[] tables = Table.values();
        int size = tables.length;
        for (int i = 0; i < size; i++) {
            Table table = tables[i];
            TabHost.TabSpec tab = tabhost.newTabSpec(getString(table.getResName()));
            View view = View.inflate(this, R.layout.layout_tabview, null);
            TextView title = (TextView) view.findViewById(R.id.tab_title);
            ImageView avatar = (ImageView) view.findViewById(R.id.tab_icon);
            Drawable drawable = getResources().getDrawable(table.getResIcon());
            avatar.setImageDrawable(drawable);
            title.setText(getString(table.getResName()));
            if (i == 2) {
                view.setVisibility(View.INVISIBLE);
                tabhost.setNoTabChangedTag(getString(table.getResName()));
            }
            tab.setIndicator(view);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(HomeActivity.this);
                }
            });
            tabhost.addTab(tab, table.getClz(), null);
            tabhost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = tabhost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = tabhost.getTabWidget().getChildAt(i);
            if (i == tabhost.getCurrentTab()) {
                v.setSelected(true);
            } else {
                v.setSelected(false);
            }
        }
        supportInvalidateOptionsMenu();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean consumed = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(tabhost.getCurrentTabView())) {
            // use getTabHost().getCurrentView() to get a handle to the view
            // which is displayed in the tab - and to get this views context
            Fragment currentFragment = getCurrentFragment();
            if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }
        }
        return consumed;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                tabhost.getCurrentTabTag());
    }

    @Override
    public void onBackPressed() {
        boolean isDoubleClick = (boolean) SPUtils.get(this, JFWConstans.KEY_EXIT_APP_DOUBLE, false);
        if (isDoubleClick) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                showToast("再按一次退出App");
            }
        }
        super.onBackPressed();
    }
}
