package com.cn.goldenjobs.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.goldenjobs.R;
import com.iyiyo.mvc.ui.fragment.BaseFragment;

import java.util.Random;

/**
 * Created by liu-feng on 2016/7/14.
 * 邮箱:w710989327@foxmail.com
 */
public class ExampleFragment extends BaseFragment {

    private int[] colors = {R.color.gold, R.color.green, R.color.purple, R.color.lightblue};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
    Bundle savedInstanceState) {
        View view = new View(getActivity());
        Random r = new Random();
        view.setBackgroundResource(colors[r.nextInt(4)]);
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
