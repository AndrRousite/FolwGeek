package com.iyiyo.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iyiyo.mvp.ui.interf.BaseFragmentInterface;
import com.iyiyo.nucleus.presenter.Presenter;
import com.iyiyo.nucleus.view.NucleusFragment;

import java.util.Calendar;

import butterknife.ButterKnife;


/**
 * Created by thanatos on 15/12/21.
 */
public abstract class BaseFragment<P extends Presenter> extends NucleusFragment<P>
        implements View.OnClickListener, BaseFragmentInterface {

    protected Activity mContext;
    protected Resources resources;
    protected LayoutInflater mInflater;
    private long lastClickTime = 0;

    public static final String BUNDLE_TYPE = "BUNDLE_TYPE";

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;  // 当前的状态（0/1/2/3/4）

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        resources = mContext.getResources();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mInflater = inflater;
        View view = inflater.inflate(getResourceId(), container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > 1000) {
            lastClickTime = currentTime;
            onClick(v.getId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showToast(String text) {
        new Handler().obtainMessage(0x1001,text).sendToTarget();
    }
}
