package com.iyiyo.mvc.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iyiyo.mvc.R;
import com.iyiyo.mvc.config.Properties;
import com.iyiyo.mvc.utils.StringUtils;
import com.iyiyo.utils.SPUtils;

import java.util.UUID;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 *
 * @author 火蚁 (http://my.oschina.net/LittleDY)
 * @version 1.0
 * @created 2014-04-22
 */
public class BaseApplication extends Application {

    public static final int PAGE_SIZE = 20;// 默认分页大小
    private static BaseApplication instance;
    private static Context _context;
    private static String lastToast = "";
    private static long lastToastTime;
    private static String LAST_REFRESH_TIME = "last_refresh_time.pref";
    private static String Read_Post_List = "read_post_list.pref";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        _context = getApplicationContext();
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static BaseApplication getInstance() {
        return instance;
    }

    /**
     * 获取Application  Context
     * @return
     */
    public static Context getContext() {
        return _context;
    }

    /**
     * 获取App唯一标识
     *
     * @return
     */
    public String getAppId() {
        String uniqueID = (String) SPUtils.get(this,Properties.UNIQUE_ID,"");
        if (StringUtils.isEmpty(uniqueID)) {
            uniqueID = UUID.randomUUID().toString();
            SPUtils.put(this, Properties.UNIQUE_ID,uniqueID);
        }
        return uniqueID;
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.BOTTOM);
    }

    public static void showToast(String message, int duration, int icon,
                                 int gravity) {
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {
                View view = LayoutInflater.from(_context).inflate(
                        R.layout.view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(_context);
                toast.setView(view);
                if (gravity == Gravity.CENTER) {
                    toast.setGravity(gravity, 0, 0);
                } else {
                    toast.setGravity(gravity, 0, 35);
                }

                toast.setDuration(duration);
                toast.show();
                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }

    public static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setExclusionStrategies(new SpecificClassExclusionStrategy(null, Model.class));
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return gsonBuilder.create();
    }

    /**
     * 记录列表上次刷新时间
     *
     * @param key
     * @param value
     * @return void
     * @author 火蚁
     * 2015-2-9 下午2:21:37
     */
    public static void putToLastRefreshTime(String key, String value) {
        SharedPreferences preferences = getPreferences(LAST_REFRESH_TIME);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取列表的上次刷新时间
     *
     * @param key
     * @return
     * @author 火蚁
     * 2015-2-9 下午2:22:04
     */
    public static String getLastRefreshTime(String key) {
        return getPreferences(LAST_REFRESH_TIME).getString(key, StringUtils.getCurTimeStr());
    }

    /**
     * 放入已读文章列表中
     *
     */
    public static void putReadedPostList( String key,
                                         String value) {
        SharedPreferences preferences = getPreferences(Read_Post_List);
        int size = preferences.getAll().size();
        SharedPreferences.Editor editor = preferences.edit();
        if (size >= 100) {
            editor.clear();
        }
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 读取是否是已读的文章列表
     *
     * @return
     */
    public static boolean isOnReadedPostList(String key) {
        return getPreferences(Read_Post_List).contains(key);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences(String prefName) {
        return _context.getSharedPreferences(prefName,
                Context.MODE_MULTI_PROCESS);
    }
}
