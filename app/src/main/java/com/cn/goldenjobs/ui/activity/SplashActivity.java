package com.cn.goldenjobs.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;

import com.cn.goldenjobs.AppControler;
import com.cn.goldenjobs.JFWApplication;
import com.cn.goldenjobs.R;
import com.cn.goldenjobs.bean.User;
import com.cn.goldenjobs.config.JFWConstans;
import com.iyiyo.utils.SPUtils;

import java.util.Date;

/**
 * Created by liu-feng on 2016/7/8.
 * 邮箱:w710989327@foxmail.com
 * https://github.com/Unlm
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadJFWConstans();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!AppControler.getInstance().isLogin()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        },2000);

    }

    private void loadJFWConstans() {
        SharedPreferences sp = SPUtils.getSharedPrefences(JFWApplication.getInstance());
        // 判断是否登录
        boolean isLogin = sp.getBoolean(JFWConstans.KEY_LOGIN_STATE, false);
        // 其次，看看登录记录是否过期
        long lastLoginTime = sp.getLong(JFWConstans.KEY_LAST_LOGIN_TIME, -1);
        if (lastLoginTime == -1) return;
        // 3天没有登录则视为过期
        if (new Date().getTime() - lastLoginTime > 3L * 24 * 60 * 60 * 1000) return;
        User user = new User();
        if (sp.getLong(JFWConstans.KEY_UID, -1) == -1) return;
        user.setUid(sp.getLong(JFWConstans.KEY_UID, -1));
        user.setName(sp.getString(JFWConstans.KEY_USERNAME, "游客"));
        user.setHeadFile(sp.getString(JFWConstans.KEY_HEADFILE, ""));
        user.setAccount(sp.getString(JFWConstans.KEY_ACCOUNT, "0"));
        user.setPassWord(sp.getString(JFWConstans.KEY_PASSWORD, null));
        user.setPhoneNumber(sp.getString(JFWConstans.KEY_PHONENUMBER, null));
        user.setPhoneName(sp.getString(JFWConstans.KEY_PHONENAME, null));
        AppControler.LOACL_LOGINED_USER = user;
    }

}
