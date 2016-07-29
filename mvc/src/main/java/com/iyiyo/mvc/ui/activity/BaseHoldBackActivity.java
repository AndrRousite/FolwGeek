package com.iyiyo.mvc.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iyiyo.mvc.R;

public abstract class BaseHoldBackActivity extends BaseActivity {

    protected Toolbar mToolbar;

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
}
