package com.cn.goldenjobs.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.cn.goldenjobs.R;
import com.iyiyo.mvp.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.layout_coordinator)
    CoordinatorLayout layoutCoordinator;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.layout_drawer)
    DrawerLayout layoutDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
//        }

        layoutDrawer.setScrimColor(Color.TRANSPARENT);  // 设置DrawerLayout背景色

        toolbar.setTitle("");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));

        setSupportActionBar(toolbar);

        navView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,layoutDrawer,toolbar,R.string.open,R.string.close);
        layoutDrawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        
        setDefaultMenuItem();

    }

    private void setDefaultMenuItem() {
        // TODO
        navView.setCheckedItem(R.id.menu_new);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        layoutDrawer.closeDrawer(navView);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
