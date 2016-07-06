package com.cn.goldenjobs.httptask;

import android.content.SharedPreferences;
import android.util.Log;

import com.cn.goldenjobs.JFWApplication;
import com.iyiyo.mvp.model.SharePreferenceManager;
import com.iyiyo.mvp.model.SharePreferenceManager.LocalUser;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.SimpleXmlConverterFactory;

/**
 * Created by liu-feng on 2016/6/21.
 * 邮箱:w710989327@foxmail.com
 */
public class API {

    private static JFWAPI jfwapi;
    private static String cookies;

    public static JFWAPI getjfwapi() {
        if (jfwapi == null) {
            OkHttpClient httpClient = new OkHttpClient();

            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    if (chain.request().url().getPath().equals("/action/api/login_validate")) {
                        Response response = chain.proceed(chain.request());
                        String cookies = response.header("Set-Cookie");
                        SharedPreferences.Editor editor = SharePreferenceManager
                                .getLocalUser(JFWApplication.getInstance()).edit();
                        editor.putString(SharePreferenceManager.LocalUser.KEY_COOKIES, cookies);
                        editor.apply();
                        clearCookies();
                        return response;
                    } else {
                        return chain.proceed(chain.request());
                    }
                }
            });

            httpClient.setConnectTimeout(4, TimeUnit.MINUTES);

            httpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept-Language", Locale.getDefault().toString())
                            .header("Host", "www.oschina.net")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", cookies == null ? getCookies() : cookies)
                            .header("User-Agent", getUserAgent())
                            .build();
                    return chain.proceed(request);
                }
            });

            jfwapi = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(SimpleXmlConverterFactory.create(new Persister(new AnnotationStrategy())))
                    .baseUrl("http://www.oschina.net/")
                    .client(httpClient)
                    .build()
                    .create(JFWAPI.class);
        }
        return jfwapi;
    }

    public static String getUserAgent() {
        return new StringBuilder("api.goldenjobs.cn")
                .append('/' + JFWApplication.getPackageInfo().versionName + '_' + JFWApplication.getPackageInfo().versionCode)// app版本信息
                .append("/Android")// 手机系统平台
                .append("/" + android.os.Build.VERSION.RELEASE)// 手机系统版本
                .append("/" + android.os.Build.MODEL) // 手机型号
                .append("/" + "WhenYouSawIt,Well!Bingo!!") // 客户端唯一标识
                .toString();
    }

    private static String getCookies() {
        if (cookies == null) {
            SharedPreferences preferences = SharePreferenceManager.getLocalUser(JFWApplication.getInstance());
            return cookies = preferences.getString(LocalUser.KEY_COOKIES, "");
        }
        return cookies;
    }

    public static void clearCookies() {
        cookies = null;
    }


    public interface JFWAPI {
        // ------------ 用户api -------------


    }
}
