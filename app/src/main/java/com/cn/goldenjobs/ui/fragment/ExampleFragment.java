package com.cn.goldenjobs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iyiyo.mvp.ui.fragment.BaseFragment;

/**
 * Created by liu-feng on 2016/7/14.
 * 邮箱:w710989327@foxmail.com
 */
public class ExampleFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = new View(getActivity());
        initView(view);
        initData();
        return view;
    }

    @Override
    public int getResourceId() {
        return 0;
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
}
