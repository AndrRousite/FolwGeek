package com.iyiyo.mvp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Hashtable;

/**
 * Created by thanatos on 16/1/26.
 */
public class BaseApplication extends Application{

    // 应用全局变量存储在这里
    protected static Hashtable<String, Object> mAppParamsHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppParamsHolder = new Hashtable<String, Object>();
    }

    /**
     * 存储全局数据
     *
     * @param key
     * @param value
     */
    public static void putValue(String key, Object value) {
        mAppParamsHolder.put(key, value);
    }

    /**
     * 获取全局数据
     *
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        return mAppParamsHolder.get(key);
    }

    /**
     * 是否已存放
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        return mAppParamsHolder.containsKey(key);
    }


}
