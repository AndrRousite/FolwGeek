package com.iyiyo.mvp.model;

import com.iyiyo.mvp.R;

/**
 * Created by liu-feng on 2016/7/22.
 * 邮箱:w710989327@foxmail.com
 */
public class ApplicationSetting {
    public static final String FILE_NAME = "APPLICATION_SETTING";
    public static final String KEY_THEME = "KEY_THEME";
    // ---主题列举---
    public enum ApplicationTheme{
        LIGHT(1, R.style.LightTheme),
        DARK(2, R.style.DarkTheme);

        private int key;
        private int resId;

        ApplicationTheme(int key, int resId){
            this.key = key;
            this.resId = resId;
        }

        public int getKey() {
            return key;
        }

        public int getResId() {
            return resId;
        }
    }
}
