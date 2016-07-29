package com.iyiyo.mvc.ui.activity;

import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iyiyo.mvc.R;
import com.iyiyo.uikit.swipeback.SwipeBackLayout;

public abstract class BaseHoldBackActivity extends BaseActivity {

    protected Toolbar mToolbar;
    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void initToolBar() {
        mToolbar = findView(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void requestFeature() {
        super.requestFeature();
        mSwipeBackLayout = new SwipeBackLayout(this);
        this.mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels);
        setSwipeBackEnable(false);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeBackLayout.attachToActivity(this);
    }

    public void onConfigurationChanged(Configuration paramConfiguration) {
        super.onConfigurationChanged(paramConfiguration);
        this.mSwipeBackLayout.setEdgeSize(getResources().getDisplayMetrics().widthPixels);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mSwipeBackLayout.findViewById(id);
    }

    public void setSwipeBackEnable(boolean enable) {
        mSwipeBackLayout.setEnableGesture(enable);
    }
}
