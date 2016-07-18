package com.iyiyo.mvc.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.iyiyo.mvc.ActivityManager;
import com.iyiyo.mvc.R;
import com.iyiyo.mvc.config.Properties;
import com.iyiyo.mvc.interf.BaseActivityInterface;
import com.iyiyo.mvc.ui.dialog.DialogControl;
import com.iyiyo.mvc.ui.dialog.DialogHelp;
import com.iyiyo.mvc.ui.toast.CommonToast;
import com.iyiyo.mvc.utils.TDevice;
import com.iyiyo.utils.SPUtils;

import java.util.Calendar;

import butterknife.ButterKnife;

/**
 * BaseActionBar Activity
 *
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @created 2014年9月25日 上午11:30:15 引用自：tonlin
 */
public abstract class BaseActivity extends AppCompatActivity implements
        DialogControl, View.OnClickListener, BaseActivityInterface {
    public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";

    private boolean _isVisible;
    private ProgressDialog _waitDialog;
    protected LayoutInflater mInflater;
    private long lastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        setContentView(getResourceId());
        mInflater = getLayoutInflater();
        // 通过注解绑定控件
        ButterKnife.bind(this);
        initToolBar();
        initView();
        initData();
        _isVisible = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.isFinishing()){
            TDevice.hideSoftKeyboard(getCurrentFocus());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ActivityManager.getActivityManager().finishActivity(this);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    public void showToast(String text) {
        new Handler().obtainMessage(0x1001,text).sendToTarget();
    }

    @Override
    public ProgressDialog showWaitDialog() {
        return showWaitDialog(R.string.loading);
    }

    @Override
    public ProgressDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public ProgressDialog showWaitDialog(String message) {
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelp.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > 1000) {
            lastClickTime = currentTime;
            onClick(v.getId());
        }
    }

    /** 查找View，这个方法可以让我们省去强转操作 */
    public <T> T findView(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) super.findViewById(id);
        return view;
    }
}
