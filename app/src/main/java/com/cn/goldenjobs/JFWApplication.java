package com.cn.goldenjobs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.os.Process;

import com.cn.goldenjobs.bean.User;
import com.cn.goldenjobs.ui.activity.SplashActivity;
import com.iyiyo.mvc.app.BaseApplication;
import com.iyiyo.utils.DensityUtils;
import com.iyiyo.utils.SystemUtils;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;

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
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());

        // ... your codes
        if (inMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
        }
    }

    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = SystemUtils.getProcessName(context);
        return packageName.equals(processName);
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = SplashActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ic_launcher;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.cn.goldenjobs/raw/msg";
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/iyiyo";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = DensityUtils.getScreenWidth(this) / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.drawable.avatar_default;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        return null;
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
