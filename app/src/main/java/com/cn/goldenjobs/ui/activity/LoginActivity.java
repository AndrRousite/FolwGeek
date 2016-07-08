package com.cn.goldenjobs.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.Toast;

import com.cn.goldenjobs.AppControler;
import com.cn.goldenjobs.R;
import com.cn.goldenjobs.bean.User;
import com.cn.goldenjobs.presenter.LoginPresenter;
import com.iyiyo.mvp.event.Events;
import com.iyiyo.mvp.event.RxBus;
import com.iyiyo.mvp.model.SharePreferenceManager;
import com.iyiyo.mvp.ui.activity.BaseHoldBackActivity;
import com.iyiyo.mvp.utils.Utilities;
import com.iyiyo.nucleus.factory.RequiresPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RequiresPresenter(LoginPresenter.class)
public class LoginActivity extends BaseHoldBackActivity<LoginPresenter> {

    @Bind(R.id.et_username)
    TextInputLayout etUsername;
    @Bind(R.id.et_password)
    TextInputLayout etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected String onSetTitle() {
        return "登录";
    }

    @Override
    protected int onBindLayout() {
        return R.layout.activity_login;
    }

    public void onLoadSuccess(User user) {
        SharedPreferences.Editor edit = SharePreferenceManager.getLocalUser(this).edit();
        edit.putLong(SharePreferenceManager.LocalUser.KEY_UID,user.getUid());
        edit.putString(SharePreferenceManager.LocalUser.KEY_USERNAME,user.getName());
        edit.putString(SharePreferenceManager.LocalUser.KEY_HEADFILE,user.getHeadFile());
        edit.putString(SharePreferenceManager.LocalUser.KEY_ACCOUNT,user.getAccount());
        edit.putString(SharePreferenceManager.LocalUser.KEY_PASSWORD,user.getPassWord());
        edit.putString(SharePreferenceManager.LocalUser.KEY_PHONENUMBER,user.getPhoneNumber());
        edit.putString(SharePreferenceManager.LocalUser.KEY_PHONENAME,user.getPhoneName());
        edit.apply();
        AppControler.LOACL_LOGINED_USER = user;
        RxBus.getInstance().send(Events.EventEnum.DELIVER_LOGIN,null);
        finish();
    }

    public void onLoadFailed(String message) {
        if (message == null){
            showToast("加载失败");
        }
        etUsername.setError(message);
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        // 校验
        String username = etUsername.getEditText().getText().toString();
        String password = etPassword.getEditText().getText().toString();
        if (Utilities.isEmpty(username) || Utilities.isEmpty(password)){
            Toast.makeText(this, "账号/密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        getPresenter().login(username,password);
    }
}
