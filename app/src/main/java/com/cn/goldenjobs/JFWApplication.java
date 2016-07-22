package com.cn.goldenjobs;

import android.os.Process;

import com.cn.goldenjobs.bean.User;
import com.iyiyo.mvc.app.BaseApplication;

/**
 * Created by
 * liu-feng on
 * 2016/6/21.
 * 邮箱:w710989327
 *
 * @foxmail.com
 */
public class JFWApplication extends BaseApplication {

    public static User LOCAL_LOGINED_USER;
    protected int mainThreadId;
    private static JFWApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        mainThreadId = Process.myTid();
        AppControler.getInstance().init();
    }

    /**
     * 是否已经登陆
     *
     * @return
     */
    public static boolean isLogined() {
        return LOCAL_LOGINED_USER != null;
    }

    public static void setLocalLoginedUser(User localLoginedUser) {
        LOCAL_LOGINED_USER = localLoginedUser;
    }

    /**
     * 获取Application实例
     *
     * @return
     */
    public static JFWApplication getInstance() {
        if (mApplication == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mApplication;
    }

    /**
     * 设置启动时间
     *
     * @param launchTime
     */
    public void setLaunchTime(long launchTime) {
        putValue("launchTime", launchTime);
    }
}
