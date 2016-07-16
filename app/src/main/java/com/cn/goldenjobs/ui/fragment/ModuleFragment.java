package com.cn.goldenjobs.ui.fragment;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.goldenjobs.R;
import com.cn.goldenjobs.bean.CityList;
import com.cn.goldenjobs.bean.User;
import com.cn.goldenjobs.httptask.APIStoreTask;
import com.iyiyo.mvp.ui.fragment.BaseFragment;
import com.iyiyo.uikit.cersamics.LazyScrollView;
import com.iyiyo.uikit.cersamics.Rotate3dAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.Bind;

/**
 * 瓷片布局的Module界面
 * Created by liu-feng on 2016/7/15.
 * 邮箱:w710989327@foxmail.com
 */
public class ModuleFragment extends BaseFragment implements LazyScrollView.OnScrollListener {
    @Bind(R.id.rootView)
    RelativeLayout rootView;
    @Bind(R.id.rootScroll)
    LazyScrollView rootScroll;

    private int rowCountPerScreen = 3;  // 行数
    private int cols = 4;// 当前总列数
    private static final int COLUMNCOUNT = 4;
    private int columnWidth = 250;// 每个item的宽度
    private int itemHeight = 0;  // 每个Item的高度

    private ArrayList<Integer> colYs = new ArrayList<Integer>();
    private ArrayList<View> Views = new ArrayList<View>();
    List<Point> lostPoint = new ArrayList<Point>();// 用于记录空白块的位置
    private LayoutInflater mInflater;

    @Override
    public int getResourceId() {
        return R.layout.fragment_module;
    }

    @Override
    public void initView(View view) {
        rootView.setPersistentDrawingCache(ViewGroup.PERSISTENT_ANIMATION_CACHE);
        rootScroll.setOnScrollListener(this);
        rootScroll.getView();
        mInflater = mContext.getLayoutInflater();
        Display display = mContext.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        Configuration cf = this.getResources().getConfiguration();
        if (cf.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rowCountPerScreen = 3;
        } else {
            rowCountPerScreen = 6;
        }
        columnWidth = width / COLUMNCOUNT;
        itemHeight = height / rowCountPerScreen;
        for (int i = 0; i < 4; i++) {
            colYs.add(0);
        }
    }

    private synchronized void addView(View view, String uri) {
        placeBrick(view);
        ImageView picView = (ImageView) view.findViewById(R.id.imageView);
        TextView textView = (TextView) view.findViewById(R.id.textView);
        rootView.addView(view);
        startAnim(view);
        textView.setText(uri);
        Glide.with(this).load(uri).error(R.mipmap.logo).into(picView);
    }

    private void startAnim(View v) {
        final float centerX = columnWidth / 2.0f;
        final float centerY = itemHeight / 2.0f;
        Rotate3dAnimation rotation = new Rotate3dAnimation(10, 0, centerX, centerY);
        rotation.setDuration(1000);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new DecelerateInterpolator());
        v.startAnimation(rotation);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onBottom() {

    }

    @Override
    public void onAutoScroll(int l, int t, int oldl, int oldt) {

    }

    /**
     * 当加载完成, 供presenter调用
     *
     * @param result
     */
    public void onLoadFinished(CityList.RetData result) {
        if (result.getCitylist() != null) {
            Random r = new Random();
            for (int i = 0; i < result.getCitylist().size(); i++) {
                View v = mInflater.inflate(R.layout.item_text_image_view, null);
                int nextInt = r.nextInt(50);
                // 模拟分为三种情况
                if (nextInt > 40) {
                    // 跨两列两行
                    android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            columnWidth * 2, itemHeight * 2);
                    v.setLayoutParams(params);
                } else if (nextInt > 30) {
                    // 跨一列两行
                    android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            columnWidth, itemHeight * 2);

                    v.setLayoutParams(params);
                } else if (nextInt > 25) {
                    // 跨两列一行
                    android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            columnWidth * 2, itemHeight);

                    v.setLayoutParams(params);
                } else {
                    android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            columnWidth, itemHeight);

                    v.setLayoutParams(params);
                }
                addView(v, result.getCitylist().get(i));
            }
        }
    }

    /**
     * 当加载失败, 供presenter调用
     *
     * @param error
     */
    public void onLoadFailure(Throwable error) {
        showToast(error != null ? error.getMessage() : "网络异常");
    }

    /**
     * 当加载中, 显示dialog
     */
    public void onLoading() {

    }

    // 布局算法

    /**
     * 原理：动态规划
     *
     * @param view
     */
    private void placeBrick(View view) {
        LayoutParams brick = (LayoutParams) view.getLayoutParams();
        int groupCount, colSpan, rowSpan;
        List<Integer> groupY = new ArrayList<Integer>();
        List<Integer> groupColY = new ArrayList<Integer>();
        colSpan = (int) Math.ceil(brick.width / this.columnWidth);// 计算跨几列
        colSpan = Math.min(colSpan, this.cols);// 取最小的列数
        rowSpan = (int) Math.ceil(brick.height / this.itemHeight);
        Log.d("VideoShowActivity", "colSpan:" + colSpan);
        if (colSpan == 1) {
            groupY = this.colYs;
            // 如果存在白块则从添加到白块中
            if (lostPoint.size() > 0 && rowSpan == 1) {
                Point point = lostPoint.get(0);
                int pTop = point.y;
                int pLeft = this.columnWidth * point.x;// 放置的left
                android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        brick.width, brick.height);
                params.leftMargin = pLeft;
                params.topMargin = pTop;
                view.setLayoutParams(params);
                lostPoint.remove(0);
                return;
            }

        } else {// 说明有跨列
            groupCount = this.cols + 1 - colSpan;// 添加item的时候列可以填充的列index
            for (int j = 0; j < groupCount; j++) {
                groupColY = this.colYs.subList(j, j + colSpan);
                groupY.add(j, Collections.max(groupColY));// 选择几个可添加的位置
            }
        }
        int minimumY;

        minimumY = Collections.min(groupY);// 取出几个可选位置中最小的添加
        int shortCol = 0;
        int len = groupY.size();
        for (int i = 0; i < len; i++) {
            if (groupY.get(i) == minimumY) {
                shortCol = i;// 获取到最小y值对应的列值
                break;
            }
        }
        int pTop = minimumY;// 这是放置的Top
        int pLeft = this.columnWidth * shortCol;// 放置的left
        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                brick.width, brick.height);
        params.leftMargin = pLeft;
        params.topMargin = pTop;
        view.setLayoutParams(params);
        if (colSpan != 1) {
            for (int i = 0; i < this.cols; i++) {
                if (minimumY > this.colYs.get(i)) {// 出现空行
                    int y = minimumY - this.colYs.get(i);
                    for (int j = 0; j < y / itemHeight; j++) {
                        lostPoint.add(new Point(i, this.colYs.get(i)
                                + itemHeight * j));
                    }
                }
            }
        }
        int setHeight = minimumY + brick.height, setSpan = this.cols + 1 - len;
        for (int i = 0; i < setSpan; i++) {
            this.colYs.set(shortCol + i, setHeight);
        }
    }
}
