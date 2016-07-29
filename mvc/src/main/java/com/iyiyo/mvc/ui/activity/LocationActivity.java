package com.iyiyo.mvc.ui.activity;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiyo.mvc.R;
import com.iyiyo.mvc.bean.Entity;
import com.iyiyo.uikit.quicksidebar.QuickSideBarTipsView;
import com.iyiyo.uikit.quicksidebar.QuickSideBarView;
import com.iyiyo.uikit.quicksidebar.listener.OnQuickSideBarTouchListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Locations
 * Created by liu-feng on 2016/7/29.
 * 邮箱:w710989327@foxmail.com
 */
public class LocationActivity extends BaseHoldBackActivity implements OnQuickSideBarTouchListener {

    RecyclerView recyclerView;
    QuickSideBarTipsView quickSideBarTipsView;
    QuickSideBarView quickSideBarView;

    private String response;
    HashMap<String, Integer> letters = new HashMap<>();
    CityListAdapter adapter;

    @Override
    public int getResourceId() {
        return R.layout.activity_location;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolbar.setSubtitle("城市列表");
    }

    @Override
    public void initView() {
        recyclerView = findView(R.id.recyclerView);
        quickSideBarTipsView = findView(R.id.quickSideBarTipsView);
        quickSideBarView = findView(R.id.quickSideBarView);
        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);
        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CityListAdapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(android.R.layout.simple_list_item_1, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(getItem(position).getCityName());
                textView.setTextColor(getResources().getColor(R.color.main_title_color));
            }
        };
    }

    @Override
    public void initData() {
        try {
            InputStream is = getAssets().open("city_list.txt");
            int ch = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream(); //实现了一个输出流
            while ((ch = is.read()) != -1) {
                out.write(ch); //将指定的字节写入此 byte 数组输出流
            }
            byte[] buff = out.toByteArray();//以 byte 数组的形式返回此输出流的当前内容
            out.close(); //关闭流
            is.close(); //关闭流
            response = new String(buff, "UTF-8"); //设置字符串编码
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response != null) {
            //GSON解释出来
            Type listType = new TypeToken<LinkedList<City>>() {
            }.getType();
            Gson gson = new Gson();
            LinkedList<City> cities = gson.fromJson(response, listType);
            ArrayList<String> customLetters = new ArrayList<>();

            int position = 0;
            for (City city : cities) {
                String letter = city.getFirstLetter();
                //如果没有这个key则加入并把位置也加入
                if (!letters.containsKey(letter)) {
                    letters.put(letter, position);
                    customLetters.add(letter);
                }
                position++;
            }

            //不自定义则默认26个字母
            quickSideBarView.setLetters(customLetters);
            adapter.addAll(cities);
            recyclerView.setAdapter(adapter);

            //            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
            //            recyclerView.addItemDecoration(headersDecor);

            // Add decoration for dividers between list items
            recyclerView.addItemDecoration(new DividerDecoration(this));
        }
    }

    @Override
    public void onClick(int v) {

    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if (letters.containsKey(letter)) {
            recyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
    }

    private abstract class CityListAdapter<VH extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<VH> {
        private ArrayList<City> items = new ArrayList<City>();

        public CityListAdapter() {
            setHasStableIds(true);
        }

        public void add(City object) {
            items.add(object);
            notifyDataSetChanged();
        }

        public void add(int index, City object) {
            items.add(index, object);
            notifyDataSetChanged();
        }

        public void addAll(Collection<? extends City> collection) {
            if (collection != null) {
                items.addAll(collection);
                notifyDataSetChanged();
            }
        }

        public void addAll(City... items) {
            addAll(Arrays.asList(items));
        }

        public void clear() {
            items.clear();
            notifyDataSetChanged();
        }

        public void remove(String object) {
            items.remove(object);
            notifyDataSetChanged();
        }

        public City getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).hashCode();
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    /**
     * RecyclerView分割条
     */
    public class DividerDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = new int[]{
                android.R.attr.listDivider
        };

        public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

        public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

        private Drawable mDivider;

        public DividerDecoration(Context context) {
            final TypedArray a = context.obtainStyledAttributes(ATTRS);
            mDivider = a.getDrawable(0);
            a.recycle();
        }

        private int getOrientation(RecyclerView parent) {
            LinearLayoutManager layoutManager;
            try {
                layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            } catch (ClassCastException e) {
                throw new IllegalStateException("DividerDecoration can only be used with a " +
                        "LinearLayoutManager.", e);
            }
            return layoutManager.getOrientation();
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            if (getOrientation(parent) == VERTICAL_LIST) {
                drawVertical(c, parent);
            } else {
                drawHorizontal(c, parent);
            }
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();
            final int recyclerViewTop = parent.getPaddingTop();
            final int recyclerViewBottom = parent.getHeight() - parent.getPaddingBottom();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int top = Math.max(recyclerViewTop, child.getBottom() + params.bottomMargin);
                final int bottom = Math.min(recyclerViewBottom, top + mDivider.getIntrinsicHeight());
                mDivider.setBounds(left, top, right, bottom);
                // c.drawColor(getResources().getColor(R.color.main_division_color));
                mDivider.draw(c);
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();
            final int recyclerViewLeft = parent.getPaddingLeft();
            final int recyclerViewRight = parent.getWidth() - parent.getPaddingRight();
            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                        .getLayoutParams();
                final int left = Math.max(recyclerViewLeft, child.getRight() + params.rightMargin);
                final int right = Math.min(recyclerViewRight, left + mDivider.getIntrinsicHeight());
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            if (getOrientation(parent) == VERTICAL_LIST) {
                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            } else {
                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            }
        }
    }

    class City extends Entity {

        /**
         * cityName : 鞍山
         * firstLetter : A
         */

        private String cityName;
        private String firstLetter;

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public void setFirstLetter(String firstLetter) {
            this.firstLetter = firstLetter;
        }

        public String getCityName() {
            return cityName;
        }

        public String getFirstLetter() {
            return firstLetter;
        }
    }
}
