package com.cn.goldenjobs.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cn.goldenjobs.R;
import com.cn.goldenjobs.bean.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iyiyo.mvc.ui.fragment.BaseFragment;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 瓷片布局的Module界面
 * Created by liu-feng on 2016/7/15.
 * 邮箱:w710989327@foxmail.com
 */
public class ModuleFragment extends BaseFragment implements OnQuickSideBarTouchListener {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.quickSideBarTipsView)
    QuickSideBarTipsView quickSideBarTipsView;
    @Bind(R.id.quickSideBarView)
    QuickSideBarView quickSideBarView;

    private String response;
    HashMap<String, Integer> letters = new HashMap<>();
    CityListAdapter adapter;

    @Override
    public int getResourceId() {
        return R.layout.fragment_module;
    }

    @Override
    public void initView(View view) {
        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);
        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
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
            }
        };
    }

    @Override
    public void initData() {
        try {
            InputStream is = mContext.getAssets().open("city_list");
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
}
