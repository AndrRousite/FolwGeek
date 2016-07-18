package com.iyiyo.mvc.config;

import android.os.Environment;

import java.io.File;

/**
 * Created by liu-feng on 2016/6/28.
 * 邮箱:w710989327@foxmail.com
 */
public class Properties {

    public final static int PageSize = 7;
    public final static String Theme_Module = "theme_module"; // 夜间风格
    public final static String UNIQUE_ID = "uniqueid"; // 手机唯一标识
    // 默认存放图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "iyiyo"
            + File.separator + "image" + File.separator;
    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "iyiyo"
            + File.separator + "download" + File.separator;
}
