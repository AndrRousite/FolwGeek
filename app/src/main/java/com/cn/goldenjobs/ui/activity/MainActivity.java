package com.cn.goldenjobs.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.cn.goldenjobs.R;
import com.dtr.zxing.activity.CaptureActivity;
import com.iyiyo.mvc.ui.activity.BaseActivity;
import com.iyiyo.mvc.ui.activity.LocationActivity;
import com.iyiyo.mvc.ui.activity.PhotoViewActivity;
import com.iyiyo.mvc.ui.activity.ShakeActivity;
import com.iyiyo.mvc.ui.activity.album.MultiImageSelector;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView
        .OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.layout_coordinator)
    CoordinatorLayout layoutCoordinator;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.layout_drawer)
    DrawerLayout layoutDrawer;
    @Bind(R.id.frame_container)
    FrameLayout frameContainer;

    ArrayAdapter mAdapter;

    private ArrayList<String> mSelectPath = new ArrayList<>();
    private static final int REQUEST_IMAGE = 1001;

    @Override
    public int getResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void initToolBar() {
        toolbar.setTitle("");
        toolbar.setSubtitle(getResources().getString(R.string.app_name));

        setSupportActionBar(toolbar);
    }

    @Override
    public void initView() {
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
        //        }

        layoutDrawer.setScrimColor(Color.TRANSPARENT);  // 设置DrawerLayout背景色
        navView.getChildAt(0).setVerticalScrollBarEnabled(false);  // 去掉侧边栏的滚动条

        navView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layoutDrawer, toolbar, R
                .string.open, R.string.close);
        layoutDrawer.setDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);

        setDefaultMenuItem();

    }

    @Override
    public void initData() {
        GridView gridView = new GridView(this);
        gridView.setNumColumns(2);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mSelectPath);
        gridView.setAdapter(mAdapter);

        frameContainer.addView(gridView);

        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(int v) {
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
        getMenuInflater().inflate(R.menu.nav_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search: //
                startActivity(new Intent(this, HomeActivity.class));
                break;
            case R.id.menu_reminder:  // 闹铃
                //startActivity(new Intent(this, PhotoAlbumActivity.class));
                MultiImageSelector selector = MultiImageSelector.create(MainActivity.this);
                selector.showCamera(true);
                selector.count(9);
                selector.multi();// 多选
                selector.origin(mSelectPath);
                selector.start(this, REQUEST_IMAGE);
                break;
            case R.id.menu_about: // 更多
                startActivity(new Intent(this, ShakeActivity.class));
                break;
            case R.id.menu_location: // 城市列表
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.menu_capture: // 二维码
                startActivity(new Intent(this, CaptureActivity.class));
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            mSelectPath.clear();
            mSelectPath.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
            StringBuilder sb = new StringBuilder();
            for (String p : mSelectPath) {
                sb.append(p);
                sb.append("\n");
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, PhotoViewActivity.class).putExtra(PhotoViewActivity
                .BUNDLE_KEY_IMAGES, mSelectPath.get(position)));
    }
}
