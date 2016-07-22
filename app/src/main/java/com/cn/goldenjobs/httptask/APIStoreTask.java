package com.cn.goldenjobs.httptask;

import android.content.SharedPreferences;

import com.cn.goldenjobs.JFWApplication;
import com.cn.goldenjobs.config.JFWConstans;
import com.iyiyo.utils.SPUtils;
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
import retrofit.http.GET;
import rx.Observable;

/**
 * 百度APIstore接口调用
 * Created by liu-feng on 2016/7/15.
 * 邮箱:w710989327@foxmail.com
 */
public class APIStoreTask {

    private static API api;
    private static String cookies; // 缓存信息

    public static API getInstance() {
        if (api == null) {
            OkHttpClient client = new OkHttpClient();
            client.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept-Language", Locale.getDefault().toString())
                            .header("Host", "apis.baidu.com")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", cookies == null ? getCookies() : cookies)
                            .header("User-Agent", getUserAgent())
                            .build();
                    return chain.proceed(chain.request());
                }
            });
            client.setConnectTimeout(10, TimeUnit.MILLISECONDS);
            api = new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory
                    .create()).addConverterFactory(SimpleXmlConverterFactory.create(
                    new Persister(new AnnotationStrategy())
            )).baseUrl("http://apis.baidu.com/apistore/").client(client)
                    .build().create(API.class);
        }
        return api;
    }

    private static String getCookies() {
        if (cookies == null) {
            cookies = (String) SPUtils.get(JFWApplication.getInstance(), JFWConstans.KEY_COOKIES, "");
            return cookies;
        }
        return cookies;
    }

    public static void clearCookies() {
        cookies = null;
    }

    public static String getUserAgent() {
        return new StringBuilder("APIStore.NET")
                .append('/' + JFWApplication.getInstance().getPackageInfo().versionName + '_' + JFWApplication.getInstance().getPackageInfo().versionCode)// app版本信息
                .append("/Android")// 手机系统平台
                .append("/" + android.os.Build.VERSION.RELEASE)// 手机系统版本
                .append("/" + android.os.Build.MODEL) // 手机型号
                .append("/" + "Yaragroovy!") // 客户端唯一标识
                .toString();
    }


    /**
     * 百度APIstore接口
     */
    public interface API {

    }
}
