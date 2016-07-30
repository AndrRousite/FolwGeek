package com.cn.goldenjobs.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cn.goldenjobs.R;
import com.cn.goldenjobs.config.JFWConstans;
import com.cn.goldenjobs.httptask.nim.ContactHttpClient;
import com.iyiyo.mvc.ui.activity.BaseHoldBackActivity;
import com.iyiyo.utils.MD5;
import com.iyiyo.utils.NetWorkUtil;
import com.iyiyo.utils.SPUtils;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;

import java.util.prefs.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseHoldBackActivity {

    @Bind(R.id.et_username)
    TextInputLayout etUsername;
    @Bind(R.id.et_password)
    TextInputLayout etPassword;
    @Bind(R.id.btn_submit)
    Button btnSubmit;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.layout_login)
    LinearLayout layoutLogin;
    @Bind(R.id.et_username_register)
    TextInputLayout etUsernameRegister;
    @Bind(R.id.et_password_register)
    TextInputLayout etPasswordRegister;
    @Bind(R.id.et_password_two_register)
    TextInputLayout etPasswordTwoRegister;
    @Bind(R.id.layout_register)
    LinearLayout layoutRegister;

    private boolean isRegister;  // 是否 是注册模式
    private AbortableFuture<LoginInfo> loginRequest;

    @Override
    public int getResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public void initToolBar() {
        super.initToolBar();
        mToolbar.setSubtitle("登录");
        if (getIntent() != null && getIntent().hasExtra("fromSplash")) {
            mToolbar.setNavigationIcon(null);
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        btnSubmit.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(int v) {
        if (v == R.id.btn_submit) {
            if (!isRegister) {  // 登录
                // 校验
                final String username = etUsername.getEditText().getText().toString();
                String password = etPassword.getEditText().getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    showToast("账号/密码不能为空", 0, 0);
                    return;
                }
                final String token = tokenFromPassword(password);
                loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo
                        (username, token));
                loginRequest.setCallback(new RequestCallback<LoginInfo>() {
                    @Override
                    public void onSuccess(LoginInfo loginInfo) {
                        onLoginDone();
                        showToast("登录成功",0,0);
                        saveLoginInfo(username, token);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailed(int code) {
                        onLoginDone();
                        if (code == 302 || code == 404) {
                            showToast("帐号或密码错误", 0, 0);
                        } else {
                            showToast("登录失败: " + code, 0, 0);
                        }
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        onLoginDone();
                        showToast("无效输入: ", 0, 0);
                    }
                });
            } else {  // 注册
                String account = etUsernameRegister.getEditText().getText().toString();
                String nickname = etPasswordRegister.getEditText().getText().toString();
                String password = etPasswordTwoRegister.getEditText().getText().toString();
                if (TextUtils.isEmpty(account) || TextUtils.isEmpty(nickname) || TextUtils
                        .isEmpty(password)) {
                    showToast("账号/昵称/密码不能为空", 0, 0);
                    return;
                }
                if (!checkRegisterContentValid()) {
                    return;
                }
                if (!NetWorkUtil.isNetworkAvailable(LoginActivity.this)) {
                    showToast(getResources().getString(R.string.network_is_not_available), 0, 0);
                    return;
                }
                ContactHttpClient.getInstance().register(account, nickname, password, new
                        ContactHttpClient.ContactHttpCallback<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                showToast(getResources().getString(R.string.register_success), 0,
                                        0);
                                switchMode();  // 切换回登录
                                etUsername.getEditText().setText(account);
                                etPassword.getEditText().setText(password);

                                etUsernameRegister.getEditText().setText("");
                                etPasswordRegister.getEditText().setText("");
                                etPasswordTwoRegister.getEditText().setText("");
                            }

                            @Override
                            public void onFailed(int code, String errorMsg) {
                                showToast(getString(R.string.register_failed,
                                        code, errorMsg), 0, 0);
                            }
                        });
            }
        } else if (v == R.id.btn_register) {
            switchMode();
        }
    }
    private void switchMode(){
        if (isRegister) {
            layoutRegister.setVisibility(View.GONE);
            layoutLogin.setVisibility(View.VISIBLE);
            btnRegister.setText("还没有账号？马上注册");
            btnSubmit.setText("登录");
            mToolbar.setSubtitle("登录");
            isRegister = false;
        } else {
            layoutRegister.setVisibility(View.VISIBLE);
            layoutLogin.setVisibility(View.GONE);
            btnRegister.setText("已有账号直接登录");
            btnSubmit.setText("注册");
            mToolbar.setSubtitle("注册");
            isRegister = true;
        }
    }

    //DEMO中使用 username 作为 NIM 的account ，md5(password) 作为 token
    //开发者需要根据自己的实际情况配置自身用户系统和 NIM 用户系统的关系
    private String tokenFromPassword(String password) {
        String appKey = readAppKey(this);
        boolean isDemo = "45c6af3c98409b18a84451215d0bdd6e".equals(appKey)
                || "fe416640c8e8a72734219e1847ad2547".equals(appKey);

        return isDemo ? MD5.getMD5(password) : password;
    }

    private static String readAppKey(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context
                    .getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo != null) {
                return appInfo.metaData.getString("com.netease.nim.appKey");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void onLoginDone() {
        loginRequest = null;
    }

    private void saveLoginInfo(final String account, final String token) {
        SPUtils.put(this, JFWConstans.KEY_USERNAME, account);
        SPUtils.put(this, JFWConstans.KEY_TOKEN, token);
    }

    private boolean checkRegisterContentValid() {
        // 帐号检查
        String account = etUsernameRegister.getEditText().getText().toString().trim();
        if (account.length() <= 0 || account.length() > 20) {
            showToast(getResources().getString(R.string.register_account_tip), 0, 0);
            return false;
        }

        // 昵称检查
        String nick = etPasswordRegister.getEditText().getText().toString().trim();
        if (nick.length() <= 0 || nick.length() > 10) {
            showToast(getResources().getString(R.string.register_nick_name_tip), 0, 0);
            return false;
        }

        // 密码检查
        String password = etPasswordTwoRegister.getEditText().getText().toString().trim();
        if (password.length() < 6 || password.length() > 20) {
            showToast(getResources().getString(R.string.register_password_tip), 0, 0);
            return false;
        }

        return true;
    }

}
