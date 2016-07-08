package com.cn.goldenjobs.presenter;

import android.os.Bundle;

import com.cn.goldenjobs.bean.RespUser;
import com.cn.goldenjobs.httptask.API;
import com.cn.goldenjobs.ui.activity.LoginActivity;
import com.iyiyo.nucleus.presenter.RxPresenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liu-feng on 2016/7/8.
 * 邮箱:w710989327@foxmail.com
 */
public class LoginPresenter extends RxPresenter<LoginActivity>{

    private static final int REQUEST_LOGIN = 1;  // 登录

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        this.<String,String,Object,Object>restartable(REQUEST_LOGIN,(username, password, o3, o4) -> {
            return API.getInstance().login(username, password, 1)
                    .subscribeOn(Schedulers.io())
                    .compose(this.<RespUser>deliverLatestCache())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(split(
                            (view, data)->{
                                if (data.getResult().getErrorCode() == 1){
                                    view.onLoadSuccess(data.getUser());
                                }else {
                                    view.onLoadFailed(data.getResult().getErrorMessage());
                                }
                            },
                            (view, error)->{
                                error.printStackTrace();
                                view.onLoadFailed(null);
                            }));
        });
    }

    public void login(String username,String password){
        start(REQUEST_LOGIN,username,password,null,null);
    }
}
