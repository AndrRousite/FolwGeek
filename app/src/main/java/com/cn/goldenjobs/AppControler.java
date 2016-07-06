package com.cn.goldenjobs;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.cn.goldenjobs.dao.DaoMaster;
import com.cn.goldenjobs.dao.DaoSession;
import com.iyiyo.utils.SPUtils;

import java.util.Hashtable;

/**
 * 描述:应用启动/退出逻辑处理类
 *
 * @author chenys
 * @since 2013-8-7 下午5:22:39
 */
public final class AppControler{

    private static AppControler mAppControler;

    private NotificationManager mNotificationManager;

    private Hashtable<String, Notification> mNotifications;

    /**
     * 数据库
     */
    private SQLiteDatabase db;

    private AppControler(JFWApplication application) {
        mNotificationManager = (NotificationManager) application
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifications = new Hashtable<String, Notification>();
    }

    public static synchronized AppControler getInstance() {
        if (mAppControler == null) {
            mAppControler = new AppControler(JFWApplication.getInstance());
        }
        return mAppControler;
    }

    /**
     * 启动软件时做初始化工作
     */
    public void init() {
        // 设置启动时间
        JFWApplication.getInstance().setLaunchTime(System.currentTimeMillis());

        // 初始化数据库
        createSqliteDatabase();

    }

    private void createSqliteDatabase() {
        db = new DaoMaster.DevOpenHelper(JFWApplication.getInstance(), "History.db", null)
                .getWritableDatabase();
    }

    /**
     * 获取数据Session，用于操作数据库
     */
    public DaoSession getDaoSession(){
        if (db == null)
            new Throwable(new Exception("数据库未初始化"));
        DaoMaster master = new DaoMaster(db);
        return master.newSession();
    }

    /**
     * 退出软件
     */
    public void exit() {
        try {
             //使用时长统计
             long launchTime = (long) JFWApplication.getInstance().getValue("launchTime");
             if (launchTime > 0) {
             long userDuration = System.currentTimeMillis() - launchTime;
                 SPUtils.put(JFWApplication.getInstance(),"launchTime",launchTime);
             }
            // 取消所有通知
            mNotificationManager.cancelAll();
            mNotifications.clear();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
